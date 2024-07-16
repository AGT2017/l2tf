<template>
  <div class="main">
    <div>你的名字:{{username}} 你的email:{{email}}</div>
    <div>留言内容：love</div>
    <!-- <div>
      <p>评论：</p>
      <a-textarea v-model:value="formState.commment" :maxlength="200" class="textarea" />
    </div> -->
    <a-button @click="commit" class="commit">提交({{ formState.commment.length }}/200)</a-button>
    <div class="list">
      <Lsit v-for="item in list" :item="item" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import Lsit from '../../components/List.vue'

const username=localStorage.getItem('username')
const email=localStorage.getItem('email')
interface List {
  name: string
  id: Number
  comment: string
  children?: List[]
}
const list = reactive<List[]>([
  {
    name: '名称1',
    id: 1,
    comment: 'love',
    children: [
      {
        name: '名称11',
        id: 11,
        comment: 'love',
        children: [
          {
            name: '名称112',
            id: 113,
            comment: 'love',
            children: [
              {
                name: '名称112',
                id: 1131,
                comment: 'love',
                children: [
                  {
                    name: '名称112',
                    id: 11311,
                    comment: 'love',
                    children: [
                      {
                        name: '名称112',
                        id: 113111,
                        comment: 'love'
                      }
                    ]
                  }
                ]
              }
            ]
          }
        ]
      },
      {
        name: '名称13',
        id: 13,
        comment: 'love'
      },
      {
        name: '步步',
        id: 14,
        comment: 'love'
      }
    ]
  },
  {
    name: '步步',
    id: 2,
    comment: 'love'
  },
  {
    name: '步步',
    id: 3,
    comment: 'love'
  },
  {
    name: '步步',
    id: 4,
    comment: 'love'
  },
  {
    name: '步步',
    id: 5,
    comment: 'love'
  },
  {
    name: '步步',
    id: 6,
    comment: 'love'
  },
  {
    name: '步步',
    id: 7,
    comment: 'love'
  },
  {
    name: '步步',
    id: 8,
    comment: 'love'
  },
  {
    name: '步步',
    id: 9,
    comment: 'love'
  },
  {
    name: '步步',
    id: 10,
    comment: 'love'
  },
  {
    name: '步步',
    id: 11,
    comment: 'love'
  },
  {
    name: '步步',
    id: 12,
    comment: 'love'
  },
  {
    name: '步步',
    id: 13,
    comment: 'love'
  },
  {
    name: '步步',
    id: 14,
    comment: 'love'
  },
  {
    name: '步步',
    id: 15,
    comment: 'love'
  },
  {
    name: '步步',
    id: 16,
    comment: 'love'
  }
])
interface FormState {
  commment: string
}
const formState = reactive<FormState>({
  commment: ''
})
const commit = () => {
  console.log(list)
}
</script>

<style scoped>
.main {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  padding: 0 20px;
}

.commit {
  width: 80%;
}

.textarea {
  width: 80%;
}

.list {
  flex: 1;
  padding: 10px 30px;
  overflow-y: auto;
}
</style>
