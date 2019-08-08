<template>
  <div class="vueSignCanvas">
    <canvas id="vueSignCanvas-c" style="width: 100%;height: 100%"></canvas>
    <div class="vueSignCanvas__btn">
      <button @click="clear">清空</button>
      <button @click="save">保存</button>
    </div>
  </div>
</template>

<script>
import { fabric } from 'fabric'
import Color from 'color'
require('./lib/point.extend')
require('./lib/util.extend')
require('./brushes/drip')
require('./brushes/stroke')
require('./brushes/ink_brush')
require('./brushes/crayon_brush')
require('./brushes/marker_brush')
// require('./brushes/spray_brush')

window.fabric = fabric

export default {
  name: 'vueSignCanvas',
  props: ['options'],
  data: () => {
    return {
      __canvas: null,
      defaultOptions: {
        width: document.documentElement.clientWidth,
        height: document.documentElement.clientHeight,
        canvasBackgroundColor: '#fff',
        brushColor: '#000',
        brushWidth: 4,
        shadowEnable: false,
        shadowWidth: 10,
        brushType: 'default'
      }
    }
  },
  methods: {
    save: function() {
      var newOptions = Object.assign({}, this.defaultOptions, this.options)
      if (newOptions.brushType === 'ink' || newOptions.brushType === 'crayon' || newOptions.brushType === 'marker') {
        this.$emit('result', this.__canvas.contextTop.canvas.toDataURL('png'))
      } else {
        this.$emit('result', this.__canvas.toDataURL('png'))
      }
    },
    clear: function() {
      const newOptions = Object.assign({}, this.defaultOptions, this.options)
      this.$emit('clear')
      this.__canvas.clear()
      this.__canvas.setBackgroundColor(newOptions.canvasBackgroundColor)
    },
    init: function() {
      var canvas = this.__canvas
      var newOptions = Object.assign({}, this.defaultOptions, this.options)
      var brushes = {}

      canvas.setBackgroundColor(newOptions.canvasBackgroundColor)
      canvas.setWidth(newOptions.width)
      canvas.setHeight(newOptions.height)

      if (canvas.freeDrawingBrush) {
        brushes = {
          ink: new fabric.InkBrush(canvas),
          crayon: new fabric.CrayonBrush(canvas),
          marker: new fabric.MarkerBrush(canvas),
          pattern: new fabric.PatternBrush(canvas),
          circle: new fabric.CircleBrush(canvas),
          pencil: new fabric.PencilBrush(canvas),
          spray: new fabric.SprayBrush(canvas)
        }
        if (
          newOptions.brushType !== 'default' &&
          brushes[newOptions.brushType]
        ) {
          canvas.freeDrawingBrush = brushes[newOptions.brushType]
        }
        canvas.freeDrawingBrush.color = newOptions.brushColor
        canvas.freeDrawingBrush.width = newOptions.brushWidth
        if (newOptions.shadowEnable) {
          canvas.freeDrawingBrush.shadow = new fabric.Shadow({
            blur: parseInt(newOptions.shadowWidth, 10) || 0,
            offsetX: 0,
            offsetY: 0,
            affectStroke: true,
            color: newOptions.shadowColor
              ? newOptions.shadowColor
              : Color(newOptions.brushColor)
                  .fade(0.5)
                  .hsl()
                  .string()
          })
        }
      }
    }
  },
  watch: {
    options: function(val) {
      this.init()
    }
  },
  mounted() {
    this.__canvas = new fabric.Canvas('vueSignCanvas-c', {
      isDrawingMode: true
    })
    fabric.Object.prototype.transparentCorners = false

    this.init()
  }
}
</script>

<style scoped >
.vueSignCanvas {
  width: 100%;
  height: 100%;
  position: relative;
}
.vueSignCanvas__btn {
  position: absolute;
  top: 0px;
  left: 0px;
  padding: 10px;
}
.vueSignCanvas__btn button {
  padding: 6px 16px;
  background: #fff;
  border: 1px solid #ececec;
  border-radius: 3px;
  margin-right: 14px;
}
@media screen and (max-width: 414px) {
  .vueSignCanvas__btn {
    position: fixed;
    bottom: 0;
    top: auto;
    left: 0;
    right: 0;
    display: flex;
    padding: 0;
  }
  .vueSignCanvas__btn button {
    flex-grow: 1;
    margin-right: 0;
    margin-left: 0;
    border-right: 0;
    border-left: 0;
    border-bottom: 0;
    border-radius: 0;
    padding: 16px;
    font-size: 16px;
  }
  .vueSignCanvas__btn button:first-child {
    border-right: 1px solid #ececec;
  }
}
</style>
