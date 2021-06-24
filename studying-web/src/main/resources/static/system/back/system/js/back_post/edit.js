layui.use(['jquery','form'],function () {
    let $ = layui.jquery,
        form  = layui.form,
        postId = $('input[name="postId"]').val(),
        index = parent.layer.getFrameIndex(window.name);
    initValue();
    function initValue() {
        $.get('/system/post/init/'+postId,function (data) {
            form.val('postForm',{
                postId: data.postId,
                postName: data.postName,
                postCode: data.postCode,
                order: data.postSort,
                postStatus: data.status,
                remark: data.remark
            });
        });
    }
    form.verify({
        postName:function(value,item){
            let exist = false;
            checkNameIsExist();
            function checkNameIsExist(){
                $.ajax({
                    async: false,
                    method: 'get',
                    url: '/system/post/checkPostNameUnique?postName='+value+'&postId='+postId,
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
                    url: '/system/post/checkPostCodeUnique?postCode='+value+'&postId='+postId,
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
            postId: data.postId,
            postName: data.postName,
            postCode: data.postCode,
            postSort: data.order,
            status: data.postStatus??0,
            remark: data.remark,
        };
        $.ajax({
            url: '/system/post/edit',
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