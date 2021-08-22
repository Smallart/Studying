layui.use(['jquery','form'],function () {
    let $  = layui.jquery,
        form = layui.form;

    form.verify({
        dictLabel:function (value) {
            if (value==null|undefined){
                return '该值不能为空';
            }
            let exit;
            checkNameIsExist();
            function checkNameIsExist(){
                $.ajax({
                    async: false,
                    method: 'get',
                    url: '/system/dict/data/checkDictLabel/'+$('input[name="dictType"]').val()+'?dictLabel='+value,
                    success:function (res) {
                        exit = res;
                    }
                });
            }
            if (exit.data){
                return '该字典标签存在';
            }
        },
        dictValue:function (value) {
            if (value==null|undefined){
                return '该值不能为空';
            }
            let exit;
            checkTypeIsExist();
            function checkTypeIsExist() {
                $.ajax({
                    async: false,
                    method: 'get',
                    url: '/system/dict/data/checkDictValue/'+$('input[name="dictType"]').val()+'?dictValue='+value,
                    success:function (res) {
                        exit = res;
                    }
                });
            }
            if (exit.data){
                return "该字典键值存在";
            }
        },
        dictSort:function (value) {
            if (value==null|undefined){
                return '该值不能为空';
            }
            if (!/^[0-9]+$/.test(value)){
                return "该值只能为数字"
            }
        }
    });

    form.on('submit(*)',function (obj) {
        $.ajax({
            url: '/system/dict/dictData/add',
            type: 'post',
            data: JSON.stringify(createParam(obj.field)),
            headers:{
                'Content-Type': 'application/json'
            },
            success:function (resp) {
                layer.msg(resp.msg);
                if(resp.code==0){
                    closecurrent();
                }
            }
        });
        return false;
    });

    function closecurrent() {
        setTimeout(function(){
            var index = index = parent.layer.getFrameIndex(window.name);
            parent.layui.studying.table(). table.reload('dictDetailTable',{
                url:'/system/dict/detail/find',
                where: {
                    dictType: $('input[name="dictType"]').val()
                }
            });
            parent.layer.close(index);
        },1000);
    }

    function createParam(data) {
        let params={
            dictCode: data.dictCode,
            dictSort: data.dictSort,
            dictLabel: data.dictLabel,
            dictValue: data.dictValue,
            dictType: data.dictType,
            isDefault: data.isDefault,
            dictDetailStatus: data.status,
            remark: data.remark,
            cssClass: data.cssClass,
            listClass: data.listClass
        };
        return params;
    }
});