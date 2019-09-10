<style scoped>
    .expand-row{
        margin-bottom: 16px;
    }
</style>
<template>
    <div>
        <Row class="expand-row" v-for="(item,index) in row.yhList">
            <Col span="3">
                <span class="expand-key">姓名: </span>
                <span class="expand-value">{{ item.yh.yhXm }}</span>
            </Col>
            <Col span="3">
                <span class="expand-key">电话: </span>
                <span class="expand-value">{{ item.yh.yhZh  }}</span>
            </Col>
            <Col span="5">
                <span class="expand-key">身份证: </span>
                <span class="expand-value">{{ item.yh.yhZjhm  }}</span>
            </Col>

            <Col span="10">
                <span class="expand-key"></span>
                <span class="expand-value">{{ item.yh.yhFpms }}</span>
            </Col>
            <Col span="3">
                <Button size="small" icon="logo-yen" type="primary" shape="circle" @click="jf(item.yhId)"></Button>
<!--                <Icon type="logo-yen" />-->
            </Col>
        </Row>
    </div>
</template>
<script>
    export default {
        props: {
            row: Object
        },
        methods:{
            jf(id){
                swal({
                    title: "确认为此学员缴费?",
                    text: "",
                    icon: "warning",
                    buttons:['取消','确认'],
                }).then((willDelete) => {
                    if (willDelete) {
                        this.save(id);
                    } else {
                    }
                });
            },
            save(id){
                this.$http.post('/api/ptyh/updateSubFee',{ids:id,km:2}).then((res)=>{
                    if (res.code == 200){
                        this.$Message.success(res.message)
                        this.$emit("getpager")
                    }else {
                        this.$Message.error(res.message)
                    }
                })
            }
        }

    };
</script>
