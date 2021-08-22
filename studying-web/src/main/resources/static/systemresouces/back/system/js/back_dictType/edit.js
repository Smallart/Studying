layui.use(['jquery','form'],function () {
    let $ = layui.jquery,
        form = layui.form;
        dictId = $('input[name="dictId"]').val();

    init();
    function init() {
        $.get('/system/dict/findDictTypeById?dictId='+dictId,function (resp) {
            if (resp.code == 0){
                let data = resp.data;
                form.val("dictTypeForm",{
                    "dictName": data.dictName,
                    "dictType": data.dictType,
                    "dictTypeStatus": data.status,
                    "remark": data.remark
                });
            }
        })
    }

    form.on('submit(*)',function (obj) {
        let params = {
            dictId: obj.field.dictId,
            status: obj.field.dictTypeStatus,
            dictName: obj.field.dictName,
            dictType: obj.field.dictType,
            remark: obj.field.remark
        };
        $.ajax({
            url: '/system/dict/dictType/edit',
            type: 'post',
            data: JSON.stringify(params),
            headers:{
                'Content-Type': 'application/json'
            },
            success:function (resp) {
                layer.msg(resp.msg);
                if (resp.code==0){
                    closeCurrent();
                }
            }
        });
    });

    function closeCurrent() {
        setTimeout(function(){
            var index = index = parent.layer.getFrameIndex(window.name);
            parent.window.location.reload();
            parent.layer.close(index);
        },1000);
    }
});