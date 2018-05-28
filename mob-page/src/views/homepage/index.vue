<template>
      <div class="box">
        <mt-header fixed title="520学车联盟"></mt-header>
        <div class="swipe">
            <mt-swipe :auto="4000">
              <mt-swipe-item>1</mt-swipe-item>
              <mt-swipe-item>2</mt-swipe-item>
              <mt-swipe-item>3</mt-swipe-item>
            </mt-swipe>
        </div>
      </div>
</template>

<script>
    import { Header, Swipe, SwipeItem } from 'mint-ui';
    export default {
        name: "index",
        components:{
          [Header.name]: Header,
          [Swipe.name]: Swipe,
          [SwipeItem.name]: SwipeItem
        }
    }
</script>

<style scoped>

</style>
