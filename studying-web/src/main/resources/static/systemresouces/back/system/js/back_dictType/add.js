layui.use(['jquery','form'],function () {
    let $ = layui.jquery,
        form = layui.form;
    // 验证
    form.verify({
        dictNameCheck:function (value) {
            if (value==null|undefined){
                return '该值不能为空';
            }
            let exit;
            checkNameIsExist();
            function checkNameIsExist(){
                $.ajax({
                    async: false,
                    method: 'get',
                    url: '/system/dict/checkDictName?dictName='+value,
                    success:function (res) {
                        exit = res;
                    }
                });
            }
            if (exit.data){
                return '该字典名称存在';
            }
        },
        dictType:function (value) {
            if (value==null|undefined){
                return '该值不能为空';
            }
            if (!/^[a-zA-Z]+_/.test(value)){
                return '字典类型不符合设置规则';
            }
            let exit;
            checkTypeIsExist();
            function checkTypeIsExist() {
                $.ajax({
                    async: false,
                    method: 'get',
                    url: '/system/dict/checkDictType?dictType='+value,
                    success:function (res) {
                        exit = res;
                    }
                });
            }
            if (exit.data){
                return "该字典类型存在";
            }
        }
    });

    // 提交
    form.on('submit(*)',function (obj) {
        let params = {
            dictId: obj.field.dictId,
            status: obj.field.dictTypeStatus,
            dictName: obj.field.dictName,
            dictType: obj.field.dictType,
            remark: obj.field.remark
        };
        $.ajax({
            url: '/system/dict/dictType/add',
            type: 'post',
            data: JSON.stringify(params),
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
            parent.window.location.reload();
            parent.layer.close(index);
        },1000);
    }
});