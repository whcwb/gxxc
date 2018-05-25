<template>
    <div>

        <div class="margin-top-20">
            <textarea id="articleEditor"></textarea>
        </div>
    </div>
</template>

<script type="application/x-javascript">
    import tinymce from 'tinymce';
    export default {
        name: "create",
        mounted(){
            console.log(tinymce);
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
        }
    }
</script>

<style scoped>

</style>