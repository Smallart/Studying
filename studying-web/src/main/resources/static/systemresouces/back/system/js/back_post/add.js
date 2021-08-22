layui.use(['jquery','form'],function () {
    let $ = layui.jquery,
        form = layui.form,
        index = parent.layer.getFrameIndex(window.name);

    form.verify({
        postName:function(value,item){
            let exist = false;
            checkNameIsExist();
            function checkNameIsExist(){
                $.ajax({
                    async: false,
                    method: 'get',
                    url: '/system/post/checkPostNameUnique?postName='+value,
                    success:function (res) {
                        exist = res;
                    }
                });
            }
            if (exist){
                return '该岗位名称已经被占用';
            }
        },
        postCode:function(value,item){
            let exist = false;
            checkNameIsExist();
            function checkNameIsExist(){
                $.ajax({
                    async: false,
                    method: 'get',
                    url: '/system/post/checkPostCodeUnique?postCode='+value,
                    success:function (res) {
                        exist = res;
                    }
                });
            }
            if (exist){
                return '该岗位编码已经被占用';
            }
        },
        order:function (value,item) {
            if (isNaN(value)){
                return "显示排序需要输入数字";
            }
        }
    });

    form.on('submit(*)',function (obj) {
        let data = obj.field;
        let params = {
            postName: data.postName,
            postCode: data.postCode,
            postSort: data.order,
            status: data.postStatus??0,
            remark: data.remark,
        };
        $.ajax({
            url: '/system/post/save',
            type: 'post',
            data: JSON.stringify(params),
            headers:{
                'Content-Type': 'application/json'
            },
            success:function (data) {
                layer.msg(data.msg);
                if (data.code==0){
                    parent.layui.layer.close(index);
                    parent.layui.table.reload('postTable',{url:'/system/post/find'});
                }
            }
        });
        return false;
    });
});