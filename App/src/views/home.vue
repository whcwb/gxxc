<template>
  <main id="home" class="view" role="main">
    <div class="heading">
      <h1><span>Mand Mobile</span></h1>
      <p>一个基于Vue的移动端UI组件库，丰富、灵活、实用，快速搭建优质的金融类产品，让复杂的金融场景变简单。</p>
    </div>
		<Button type="primary">Primary</Button>
    <!--<md-button @click="handleClick">点我</md-button>-->
  </main>
</template>

<script>
	import { Toast } from 'mand-mobile'
	import {Button} from 'iview'
  export default {
    name: 'home-view',
    components: {
    	Button
    },
    methods: {
      handleClick() {
        Toast.info('不错哟~')
      }
    }
  }
</script>
