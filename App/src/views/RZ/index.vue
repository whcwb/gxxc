<style lang="less">
  @import "./rz";
</style>
<template>
  <div id='rz' class="box">
    <headTit tit="证件上传"></headTit>
    <div class="body-D imgcenter">
      <div class="md-example-child md-example-child-reader md-example-child-reader-0">
        <ul class="image-reader-list">
          <li
            class="image-reader-item"
            v-for="(img, index) in imageList['reader0']"
            :key="index"
            :style="{
              'backgroundImage': `url(${img})`,
              'backgroundPosition': 'center center',
              'backgroundRepeat': 'no-repeat',
              'backgroundSize': 'cover'
            }">
            <md-icon
              class="image-reader-item-del"
              name="circle-cross"
              color="#666"
              @click.native="onDeleteImage('reader0', index)">
            </md-icon>
          </li>
          <li class="image-reader-item add" v-show="imageList['reader0'].length<4">
            <md-image-reader
              name="reader0"
              @select="onReaderSelect"
              @complete="onReaderComplete"
              @error="onReaderError"
              is-multiple
            ></md-image-reader>
            <md-icon name="hollow-plus" size="md" color="#CCC"></md-icon>
            <p>{{zjimg}}</p>
          </li>
        </ul>
      </div>

    </div>
    <div style="padding:0.3rem ">
      <Button type="error" long
              style="padding: 0.18rem">
        证件上传</Button>
    </div>
  </div>
</template>

<script>
  import {Icon, ImageReader, Toast} from 'mand-mobile'
  import {Button} from 'iview'
  import headTit from '../comp/headTit'
  export default {
    name: 'image-reader-demo',
    /* DELETE */
    title: '图片选择',
    /* DELETE */
    components: {
      Button,
      headTit,
      [Icon.name]: Icon,
      [ImageReader.name]: ImageReader,
    },
    data() {
      return {
        zjimg:'请拍摄身份证正面',
        imageList: {
          reader0: [
            // '//img-hxy021.didistatic.com/static/strategymis/insurancePlatform_spu/uploads/27fb7f097ca218d743f816836bc7ea4a',
            // '//manhattan.didistatic.com/static/manhattan/insurancePlatform_spu/uploads/c2912793a222eb24b606a582fd849ab7',
          ],
          reader1: [],
        },
      }
    },
    computed:{
      zjimgLength:function() {
        return this.imageList.reader0.length
      }
    },
    watch:{
      imageList:function (n,o) {
        console.log('数据坚挺',n)
        // switch (n) {
        //   case 1:
        //     this.zjimg='请拍摄身份证正面';
        //       break;
        //   case 2:
        //     this.zjimg='请拍摄身份证反面';
        //       break;
        //   case 3:
        //     this.zjimg='请拍摄驾驶证正面';
        //       break;
        //   case 3:
        //     this.zjimg='请拍摄驾驶证反面';
        //       break;
        //   default:
        //       this.zjimg='请拍摄身份证正面';
        //       break;
        // }

      }
    },
    created(){

    },
    methods: {
      onReaderSelect() {
        Toast.loading('图片读取中...')
      },
      onReaderComplete(name, {dataUrl}) {
        const demoImageList = this.imageList[name] || []

        demoImageList.push(dataUrl)
        this.$set(this.imageList, name, demoImageList)

        Toast.hide()
        // debugger
        let img =this.imageList.reader0.length
        switch (img) {
          case 1:
            this.zjimg='请拍摄身份证反面';
            break;
          case 2:
            this.zjimg='请拍摄驾驶证正面';
            break;
          case 3:
            this.zjimg='请拍摄驾驶证反面';
            break;
          default:
              this.zjimg='请拍摄身份证正面';
              break;
        }
      },
      onReaderError({msg}) {
        Toast.failed(msg)
      },
      onDeleteImage(name, index) {
        const demoImageList = this.imageList[name] || []
        demoImageList.splice(index, 1)
        this.$set(this.imageList, name, demoImageList)
      },
    },
  }

</script>

<style lang="stylus" scoped>
  .md-example-child-reader
    .image-reader-list
      float left
      width 100%
      .image-reader-item
        border solid 1px #dedede
        position relative
        float left
        width 23.5%
        padding-bottom 23.5%
        margin-bottom 2%
        margin-right 2%
        background color-bg-base
        box-sizing border-box
        list-style none
        border-radius radius-normal
        hairline(all, color-border-base)
        background-size cover
        &:nth-of-type(4n)
          margin-right 0
        &.add
          .md-icon
            position absolute
            top 40%
            left 50%
            transform translate(-50%, -50%)
            opacity .5
          p
            position absolute
            top 50%
            left 0
            width 100%
            margin-top 15px
            font-size font-minor-normal
            color color-text-disabled
            text-align center
        .image-reader-item-del
          position absolute
          top 5px
          right 5px
          z-index 3
          background #EEE
          border-radius radius-circle
</style>
