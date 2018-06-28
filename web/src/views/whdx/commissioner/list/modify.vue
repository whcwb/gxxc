<style type="text/css">

</style>
<template>
	<div>
		<Modal v-model="showModal" width='1200' :closable='false'
			:mask-closable="false" :title="operate+''">
			<div style="overflow: auto;height: 500px;">
				<Form ref="form"
						:model="formItem"
						:rules="ruleInline"
						:label-width="100"
						:styles="{top: '20px'}">
					<Row>
						<form-items :parent="v"></form-items>
					</Row>
				</Form>
			</div>
			<div slot='footer'>
				<Button type="ghost" @click="v.util.closeDialog(v)">取消</Button>
				<Button type="primary" @click="v.util.save(v)">确定</Button>
			</div>
		</Modal>
	</div>
</template>

<script>
	import chooseImg from '../../components/chooseImg'
	export default {
		name: 'byxxForm',
		components:{
		    chooseImg
		},
		data() {
			return {
			    v:this,
                operate:'专员',
				saveUrl:this.apis.teacher.CHANGE,
                staticPath:this.apis.getImgUrl,
				// uploadPrivatePath:this.apis.UPLOAD_PRIVATE,
				uploadPrivatePath:this.apis.UPLOAD,
				showModal: true,
				readonly: false,
                files:{
                    cardFront:'',
                    cardBack:'',
                    licenceFront:'',
                    licenceBack:''
                },
				formItem: {

				},
                formInputs:[
                    {label:'姓名',prop:'yhXm'},
                    {label:'身份证号码',prop:'yhZjhm'},
                    {label:'手机号码',prop:'yhSjhm',disables:true},
                    {label:'区域',prop:'jlQu',dict:'ZDCLK0060'},
                    {label:'联系电话',prop:'jlJjlxrdh'},
                    {label:'地址',prop:'jlZz'},

                ],
                ruleInline:{
				},
				yhLx:'',
			}
		},
        /**
		 * yhXm:姓名
         yhZjhm:320333333333333333
         yhSjhm:18672922222
         jlJl:11
         jlQu:10
         jlZml:证明人
         jlJjlxr:紧急联系人
         jlJjlxrdh:联系电话
         jlZz:专员地址
         jlImg:/aaa.jpg
         jlMs:专员简界
         imgList:./aaa.jpg,./bbb.jpg,-,-
         yhMm:
         */
		created(){
		    this.util.initFormModal(this);
		},
		methods: {
		    getData(id){
		      this.$http.get(this.apis.teacher.getById+id).then((res)=>{
		          if (res.code == 200 && res.result){
		              this.formItem.jlQu = res.result.jlQu
                      console.log(this.formItem);
                      this.util.initFormRule(this);
                      this.util.initForeignKeys(this);
                      this.util.initDict(this)
                  }
              })
			},
		    beforeSave(){
            },
            imgChange(o){
		        let type = o.type;
		        this.files[type] = o.path;
		        this.addImg(type,path);
            },
			addImg(type,path){
		        let t = "cardFront";
		        switch(type){
					case "cardFront": t = "10";break;
					case "cardBack": t = "11";break;
					case "licenceFront": t = "20";break;
					case "licenceBack": t = "21";break;
				}
		        let params = {
                    yhId:this.formItem.yhId,
                    wjTpdz:path,
                    wjSx:t
				}
                this.$http.post(this.apis.wj.ADD,params).then((res)=>{
                    if (res.code === 200 && res.result){
                        for (let r of res.result){
                            switch(r.wjSx){
                                case '10':
                                    v.files.cardFront = r.wjTpdz;
                                    break;
                                case '11':
                                    v.files.cardBack = r.wjTpdz;
                                    break;
                                case '20':
                                    v.files.licenceFront = r.wjTpdz;
                                    break;
                                case '21':
                                    v.files.licenceBack = r.wjTpdz;
                                    break;
                                default:
                            }
                        }
                        console.log(v.files);
                    }
                })
			}
		}
	}
</script>

<style>

</style>
