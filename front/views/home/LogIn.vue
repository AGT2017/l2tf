<template>
  <div class="main">
    <Form :type="2" @onFinish="onFinish" />
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import Form from './Form.vue'

const router = useRouter()
function jump() {
  router.push({
    path: '/home'
  })
}
const onFinish = function (data: any) {
  console.log(data, '------------------')
  jump()
}
</script>

<style scoped>
.main {
  width: 100%;
  height: 100%;
}
</style>
