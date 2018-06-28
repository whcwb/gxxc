<template>
  <div class="box_col" style="background-color: #fff">
    <VueSignCanvas :options="options" @result="saveResult" @clear="clear" />
  </div>

</template>

<script>
    import VueSignCanvas from 'vue-sign-canvas'
    require('vue-sign-canvas/dist/vue-sign-canvas.min.css')
    export default {
        name: "signCanvas",
        components:{
          VueSignCanvas
        },
        data(){
          return{
            options:{
              brushColor: '#000',
              brushWidth: 10,
              brushType: 'marker',
              width: 500,
              height: 500,
              shadowEnable: false,
              shadowWidth: 10,
              canvasBackgroundColor: '#fff0',
            }
          }
        },
        methods:{
          saveResult (data) {
            // let mess = parseJSON(data)
              console.log('VueSignCanvas*****-----',data)
            console.log('-----',data.substring(22))
          },
          clear () {
            console.log('clear');
          }
        }
    }
</script>

<style scoped>

</style>
