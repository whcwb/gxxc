<template>
  <div id="app">
    <!--<transition name="bounce">-->
        <router-view/>
    <!--</transition>-->
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
  @import "//at.alicdn.com/t/font_683602_rys1c80mziee9udi.css";
  html, body, #app{
    padding: 0;
    margin: 0;
    height: 100%;
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
  }
  .bounce-enter-active {
    animation: bounce-in 3s;
  }
  .bounce-leave-active {
    animation: bounce-out 3s;
  }
  @keyframes bounce-in {
    20% {
      opacity: 1;
      -webkit-transform: translate3d(-20px, 0, 0);
      transform: translate3d(-20px, 0, 0)
    }
    to {
      opacity: 0;
      -webkit-transform: translate3d(2000px, 0, 0);
      transform: translate3d(2000px, 0, 0)
    }
  }
  @keyframes bounce-out {
    20% {
      opacity: 1;
      -webkit-transform: translate3d(20px, 0, 0);
      transform: translate3d(20px, 0, 0)
    }
    to {
      opacity: 0;
      -webkit-transform: translate3d(-2000px, 0, 0);
      transform: translate3d(-2000px, 0, 0)
    }
  }
</style>
