layui.define(['jquery','table','form','laydate'],function (exports) {
    let $ = layui.jquery,
        table = layui.table,
        form = layui.form,
        laydate = layui.laydate,
        // 默认配置
        config = {
            initType: 'both',
            date:{
                startTime:'#create-start-time',
                endTime: '#create-end-time',
                dateForm: 'yyyy-MM-dd HH:mm:ss'
            },
            table:{
                elem: undefined,
                height: undefined,
                url: undefined,
                request:{
                    pageName: 'limit',
                    limitName: 'offset'
                },
                page: true,
                skin: 'line',
                cols: undefined,
                parseData:function (res) {
                    return{
                        "code":res.code,
                        "msg":res.msg,
                        "count":res.data.total,
                        "data": res.data.data
                    }
                },
                done: undefined
            }
        };

    function PageCommon(options) {
        this.render(options)
    }

    PageCommon.prototype.render = function (options) {
        this.options = $.extend(true,config,options);
        let initType = this.options.initType;
        if (initType==='date'){
            this.initDate();
        }else if(initType==='table'){
            this.initTable();
        }else{
            this.initDate();
            this.initTable();
        }
    };
    /**
     * 初始化laydateinput
     */
    PageCommon.prototype.initDate = function () {
        let config = this.options.date;
        let startTime = laydate.render({
            elem: config.startTime,
            type: 'datetime',
            form: config.dateForm,
            done:function (value,dates,endDate) {
                endTime.config.min = {
                    year: dates.year,
                    month: dates.month - 1,
                    date: dates.date
                };
            }
        });
        let endTime = laydate.render({
            elem: config.endTime,
            type: 'datetime',
            form: config.dateForm,
            done:function (value,dates,endDate) {
                startTime.config.max = {
                    year: dates.year,
                    month: dates.month - 1,
                    date: dates.date
                };
            }
        });
    };

    /**
     * 初始化table
     */
    PageCommon.prototype.initTable = function(){
        table.render(this.options.table);
    };

    let obj = {
        render:function (options) {
            return new PageCommon(options);
        },
        tableReload:function (elem,url,param) {
            table.reload(elem,{
                url:url,
                where:param
            });
        },
        on:function (type,filter,callback) {
            if (type==='form'){
                form.on(filter,callback);
            }else if (type === 'table'){
                table.on(filter,callback);
            }
        }
    };

    exports('studyingPageCommon',obj);
});