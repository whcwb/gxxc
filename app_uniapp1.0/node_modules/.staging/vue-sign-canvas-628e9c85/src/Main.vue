<template>
  <div>
    <App :options="options" @result="save"></App>
    <button class="btn" @click="changeColor">随机更换颜色</button>
  </div>
</template>

<script>
import App from './App.vue'
export default {
  name: 'main',
  data: () => {
    return {
      options: {
        // height: window.screen.availHeight - 200,
        // canvasBackgroundColor: '#000',
        // brushColor: '#f00',
        // brushColor: 'rgb(168,17,139)',
        brushWidth: 20,
        shadowEnable: false,
        shadowWidth: 10,
        brushType: 'ink'
      }
    }
  },
  methods: {
    save: function(data) {
      this.downloadFile(data)
    },
    downloadFile(url) {
      var aLink = document.createElement('a')
      aLink.href = url
      aLink.download = 'text.png'
      aLink.id = 'img1'
      document.body.appendChild(aLink)
      document.getElementById('img1').click()
      document.getElementById('img1').remove()
    },
    getColor: function Color() {
      this.r = Math.floor(Math.random() * 255)
      this.g = Math.floor(Math.random() * 255)
      this.b = Math.floor(Math.random() * 255)
      return 'rgb(' + this.r + ',' + this.g + ',' + this.b + ')'
    },
    changeColor: function(data) {
      this.options = Object.assign({}, this.options, {
        brushColor: this.getColor()
      })
    }
  },
  components: {
    App: App
  }
}
</script>

<style>
body {
  padding: 0;
  margin: 0;
  /* background: #000; */
}
.btn {
  position: absolute;
  top: 10px;
  right: 10px;
  padding: 6px 16px;
  background: #fff;
  border: 1px solid #ccc;
  border-radius: 3px;
  margin-right: 14px;
}
/* .vueSignCanvas__btn {
  bottom: -40px;
  top: auto!important;
} */
</style>
