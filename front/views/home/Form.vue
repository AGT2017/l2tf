<!--
 * @Description: 
 * @Author: baiyang
 * @Date: 2024-07-11 14:56:15
 * @LastEditTime: 2024-07-12 10:02:22
 * @LastEditors: baiyang
-->
<template>
  <div class="form">
    <h1 class="name">{{ props.type === 1 ? '登陆' : '注册' }}</h1>
    <a-form :model="formState" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }" autocomplete="off"
      name="nest-messages" :validate-messages="validateMessages" @finish="onFinish">
      <a-form-item label="name" name="name">
        <a-input v-model:value="formState.username" />
      </a-form-item>
      <a-form-item label="email" name="email" :rules="[{ type: 'email' }]">
        <a-input v-model:value="formState.email" />
      </a-form-item>
      <a-form-item label="Password" name="password"
        :rules="[{ required: true, message: 'Please input your password!' }]">
        <a-input-password v-model:value="formState.password" />
      </a-form-item>

      <a-form-item v-if="props.type === 1" name="remember" :wrapper-col="{ offset: 8, span: 16 }">
        <a-checkbox v-model:checked="formState.remember">Remember me</a-checkbox>
      </a-form-item>

      <a-form-item :wrapper-col="{ offset: 8, span: 16 }">
        <a-button type="primary" html-type="submit" class="mr-12">{{
          props.type === 1 ? '登陆' : '注册'
        }}</a-button>
        <a-button @click="go">{{ props.type === 1 ? '去注册 ' : '去登陆' }}</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>
<script lang="ts" setup>
import { reactive, getCurrentInstance, defineProps, defineEmits, ComponentInternalInstance } from 'vue'
import { useRouter } from 'vue-router'

const emits = defineEmits(['onFinish'])
const props = defineProps({
  type: {
    type: Number,
    default: 1
  }
})
const router = useRouter()

interface FormState {
  username: string
  email: string
  password: string
  remember: boolean
}
const { proxy } = getCurrentInstance() as ComponentInternalInstance
const formState = reactive<FormState>({
  username: '',
  email: '',
  password: '',
  remember: true
})

const validateMessages = {
  required: '${label} is required!',
  types: {
    email: '${label} is not a valid email!'
  }
}
// 用户名正则，4到16位（字母，数字）
const uName = /^[a-zA-Z0-9]{5,20}$/
// 密码强度正则，8到20位，包括至少1个大写字母，1个小写字母，1个数字，1个特殊字符
const pPassword = /^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*?[\.\!\~\_@#$])[a-zA-Z0-9\.\!\~_@#$]{8,20}$/
const onFinish = () => {
  if (!formState.username && !formState.email) return proxy.$message.error('name与email必须填一个')
  if (formState.username) {
    if (!uName.test(formState.username)) {
      return proxy.$message.error('请输入5到20位字母或者数字的name')
    }
  }
  if (formState.password) {
    if (!pPassword.test(formState.password)) {
      return proxy.$message.error(
        '请输入8到20位包括并至少1个大写字母，1个小写字母，1个数字，1个特殊字符的password'
      )
    }
  }
  emits('onFinish', formState)
}

function go() {
  const path = props.type === 1 ? '/login' : '/home'
  router.push({
    path
  })
}
</script>

<style scoped>
.form {
  display: flex;
  flex-direction: column;
  padding: 20px;
}

.name {
  font-size: 28px;
  font-weight: 700;
}

.mr-12 {
  margin-right: 12px;
}
</style>
