<template>
  <div class="main">
    <Form :type="1" @onFinish="onFinish" />
  </div>
</template>

<script setup lang="ts">
import { instance, login, register } from "@/libs/api/index";

import { useRouter } from "vue-router";
import Form from "./Form.vue";

const router = useRouter();
function jump() {
  router.push({
    path: "/comment"
  });
}
const onFinish = async function (data: any) {
  if (data.type === 1) {
    const result = await login(data);
    console.log(`login:${result}`)
    if (result.data.url === "hello") {
      // this.$toast.success("登录成功");
      // this.$store.commit("setUser", data.data.token);
      // this.$router.push({ name: "mychongzhi" });
      localStorage.setItem('username', result.data.userInfo.username);
      localStorage.setItem('email', result.data.userInfo.email);
      jump();
    }
  } else {
    const result = await register(data.form);
    console.log(`register:${result}`)
    // if (result.data.url === "hello") {
    //   // this.$toast.success("登录成功");
    //   // this.$store.commit("setUser", data.data.token);
    //   // this.$router.push({ name: "mychongzhi" });
    //   // localStorage.setItem('username', result.data.userInfo.username);
    //   // localStorage.setItem('email', result.data.userInfo.email);
    //   // jump();

    // }
    // console.log(data);
    jump();
  }

};
</script>

<style scoped>
.main {
  width: 100%;
  height: 100%;
}
</style>
