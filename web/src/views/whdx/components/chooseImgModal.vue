<template>
	<div>
	<Modal v-model="showModal" width='900' :closable="false" @on-cancel="close" @on-ok="ok" title="选取图片">
		<choose-img v-if="loadComponent" :type="model.type" :path="model.path" @imgChange="imgChange"></choose-img>
	</Modal>
	</div>
</template>
<script>
	import chooseImg from './chooseImg'

    export default {
    	name:'',
		components:{
    	    chooseImg
		},
        data () {
            return {
                showModal:true,
                loadComponent:false,
				model:{
                    type:'',
					path:''
				}
            }
        },
        created(){
            if (this.$parent.choosedImgs){
    	        this.model.path = this.$parent.choosedImgs;
                this.loadComponent = true;
			}
        },
        methods: {
            imgChange(o){
                this.model = o;
            },
            ok(){
                this.$emit('chooseImgFinish',this.model.path)
                this.close()
            },
            close(){
                let v = this;
                v.showModal = false;
                setTimeout(() => {
                    v.$parent.$data.componentName = "";
                }, 200)
            }
        },
        mounted () {
        }
    }
</script>
