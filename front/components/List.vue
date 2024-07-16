<template>
  <div>
    <div>{{ props.item.name }}: {{ props.item.comment }}---{{ props.item.id }}</div>
    <div class="commment" :class="`${props.item.id}-${props.item.name}`">
      <a-button @click="show" :class="['commit']">回复</a-button>
      <div v-show="display">
        <a-input v-model:value="commmentStr" class="input" :maxlength="200">
          <template #suffix> {{ commmentStr.length }} / 200 </template>
        </a-input>
        <a-button type="primary" @click="commit(props.item, {'name': username, 'comment': commmentStr, 'id':'111', 'children': []})" class="commit">确定</a-button>
      </div>
    </div>
    <div v-if="props.item.children" class="pl-12">
      <Lsit v-for="v in props.item.children" :key="v.id" :item="v" />
    </div>
  </div>
</template>

<script>
export default {
  name: 'Lsit'
}
</script>

<script setup>
import { defineProps, ref, onUnmounted } from 'vue'

const username=localStorage.getItem('username')

const handleClick = (event) => {
  const path = event.composedPath ? event.composedPath() : event.path
  if (
    !path.some((item) => {
      return item.classList && [...item.classList].includes(`${props.item.id}-${props.item.name}`)
    })
  ) {
    display.value = false
    document.removeEventListener('click', handleClick)
  }
}
onUnmounted(() => {
  document.removeEventListener('click', handleClick)
})
const commmentStr = ref('')
const display = ref(false)
const props = defineProps({
  item: {
    type: Object,
    default: () => {}
  }
})
const show = () => {
  display.value = true
  document.addEventListener('click', handleClick)
}
const commit = (target, item) => {
  target.children.unshift(item)
  display.value = true
  document.removeEventListener('click', handleClick)
}
</script>

<style scoped>
.commit {
  width: 100%;
}

.pl-12 {
  padding-left: 12px;
}

.input {
  height: 60px;
}
</style>
