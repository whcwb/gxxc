<style lang="less">
    @import '../../../styles/common.less';
</style>

<template>
    <div>
        <Row>
            <Col span="18">
                <Card>
                    <label style="display: inline-block">文章标题：</label>
                    <Input v-model="form.hdBt" icon="md-list"/>
                    <div class="margin-top-20">
                        <textarea id="articleEditor"></textarea>
                    </div>
                </Card>
            </Col>
            <Col span="6" class="padding-left-10">
                <Card>
                    <p slot="title">
                        <Icon type="md-paper-airplane"></Icon>
                        发布
                    </p>
                    <Row>
                        <label>活动属性</label>
                        <Select v-model="form.hdSx">
                            <Option value="1">驾校</Option>
                            <Option value="2">训练场</Option>
                        </Select>
                    </Row>

                    <Row class="margin-top-20 publish-button-con">
                        <span class="publish-button"><Button @click="publish"  icon="ios-checkmark" style="width:90px;" type="primary">发布</Button></span>
                    </Row>
                </Card>
            </Col>
        </Row>
    </div>
</template>

<script>
    import tinymce from 'tinymce';
    export default {
        name: 'artical-publish',
        data () {
            return {
                form:{
                    id:'',
                    hdZw:'',
                    hdBt:'',
                    hdSx:'',
                },
            };
        },
        methods: {
        },
        computed: {
        },
        mounted () {
            tinymce.init({
                selector: '#articleEditor',
                branding: false,
                elementpath: false,
                height: 600,
                language: 'zh_CN.GB2312',
                menubar: 'edit insert view format table tools',
                theme: 'modern',
                plugins: [
                    'advlist autolink lists link image charmap print preview hr anchor pagebreak imagetools',
                    'searchreplace visualblocks visualchars code fullscreen fullpage',
                    'insertdatetime media nonbreaking save table contextmenu directionality',
                    'emoticons paste textcolor colorpicker textpattern imagetools codesample'
                ],
                toolbar1: ' newnote print fullscreen preview | undo redo | insert | styleselect | forecolor backcolor bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image emoticons media codesample',
                autosave_interval: '20s',
                image_advtab: true,
                table_default_styles: {
                    width: '100%',
                    borderCollapse: 'collapse'
                }
            });
            if (this.$route.params.item){
                this.form = JSON.parse(this.$route.params.item);
                console.log(this.form);
                let content = tinymce.activeEditor.setContent(this.form.hdZw);
            }
        },
        destroyed () {
            tinymce.get('articleEditor').destroy();
        },
        methods:{
            publish(){
                let content = tinymce.activeEditor.getContent();
                this.form.hdZw = content;
                let url = this.apis.hd.ADD;
                if (this.$route.params.item){
                    url = this.apis.hd.CHANGE;
                }
                this.$http.post(url,this.form).then((res)=>{
                    if (res.code === 200){
                        this.$Message.success("发布成功");
                    }else{
                        this.$Message.error(res.message);
                    }
                })
            }
        }
    };
</script>
