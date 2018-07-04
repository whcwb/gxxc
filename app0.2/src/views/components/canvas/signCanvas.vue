<template>
  <div class="box_col" style="background-color: #fff">
    <VueSignCanvas :options="options" @result="saveResult" @clear="clear" />
  </div>

</template>

<script>
    import VueSignCanvas from 'vue-sign-canvas'
    require('vue-sign-canvas/dist/vue-sign-canvas.min.css')
    import { Toast } from 'mint-ui'
    export default {
        name: "signCanvas",
        components:{
          VueSignCanvas
        },
        data(){
          return{
            options:{
              brushColor: '#ff2600',
              brushWidth: 50,
              brushType: 'marker',
              width: 500,
              height: 600,
              shadowEnable: false,
              shadowWidth: 10,
              canvasBackgroundColor: '#fff',
            }
          }
        },
        mounted(){
            this.options.height = window.innerHeight
        },
        methods:{
          saveResult (data) {
            // let mess = parseJSON(data)
              console.log('VueSignCanvas*****-----',data)
            console.log('-----',data.substring(22))
            this.$http.post(this.apis.SIGN,{base64Data:data.substring(22)}).then((res)=>{
              if(res.code==200){
                // Toast('签名成功，请缴费')
                this.$emit('saveResult',res.result)
              }else {
                Toast(res.message)
              }
            }).catch((err)=>{

            })
          },
          clear () {
            console.log('clear');
          }
        }
    }
</script>

<style scoped>

</style>
