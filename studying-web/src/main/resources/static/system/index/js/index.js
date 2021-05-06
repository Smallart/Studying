layui.config({
    base: '/expandjs/'
}).extend({
    echarts: 'echarts/echarts'
}).use(['echarts','element'],function () {
    var echarts = layui.echarts,dailyTask,tasksTime
        ,dailyTasksPrecent,compareWithYesterday,
        weekTasks,monthTasks;

    init();

    function init() {
        initDailyTasks();
        initWeekTasks();
        initMonthTasks();
        initTaskTime();
        initDailyTaskPresent();
        initCompareYesterDay();
    }
    // 日任务
    function initDailyTasks() {
        dailyTask = taskItems('.dailyTasks .chart');
    }
    
    function taskItems(dom) {
        let myColor = ["#1089E7", "#F57474", "#56D0E3", "#F8B448", "#8B78F6"];
        // 1. 实例化对象
       var myEcharts = echarts.init(document.querySelector(dom));
        // 2. 指定配置和数据
        let option = {
            grid: {
                top: "10%",
                left: "22%",
                bottom: "10%"
                // containLabel: true
            },
            // 不显示x轴的相关信息
            xAxis:  {
                show: false
            },
            yAxis: [
                {
                    type: "category",
                    inverse: true,
                    data: ["HTML5", "CSS3", "javascript", "VUE", "NODE"],
                    // 不显示y轴的线
                    axisLine: {
                        show: false
                    },
                    // 不显示刻度
                    axisTick: {
                        show: false
                    },
                    // 把刻度标签里面的文字颜色设置为白色
                    axisLabel: {
                        color: "#fff"
                    },
                    animationDuration: 300,
                    animationDurationUpdate: 300,
                    max:3
                },
                {
                    data: [100, 100, 100, 100, 120],
                    inverse: true,
                    // 不显示y轴的线
                    axisLine: {
                        show: false
                    },
                    // 不显示刻度
                    axisTick: {
                        show: false
                    },
                    // 把刻度标签里面的文字颜色设置为白色
                    axisLabel: {
                        color: "#fff"
                    },
                    animationDuration: 300,
                    animationDurationUpdate: 300,
                    max:3
                }
            ],
            series: [
                {
                    realtimeSort:true,
                    name: "条",
                    type: "bar",
                    data: [70, 34, 60, 78, 100],
                    yAxisIndex: 0,
                    // 修改第一组柱子的圆角
                    itemStyle: {
                        barBorderRadius: 20,
                        // 此时的color 可以修改柱子的颜色
                        color: function(params) {
                            // params 传进来的是柱子对象
                            // console.log(params);
                            // dataIndex 是当前柱子的索引号
                            return myColor[params.dataIndex];
                        }
                    },
                    // // 柱子之间的距离
                    barCategoryGap: 50,
                    //柱子的宽度
                    barWidth: 10,
                    // 显示柱子内的文字
                    label: {
                        show: true,
                        position: "inside",
                        // {c} 会自动的解析为 数据  data里面的数据
                        formatter: "{c}%"
                    }
                },
                {
                    name: "框",
                    type: "bar",
                    barCategoryGap: 50,
                    barWidth: 15,
                    yAxisIndex: 1,
                    data: [100, 100, 100, 100, 100],
                    itemStyle: {
                        color: "none",
                        borderColor: "#00c1de",
                        borderWidth: 3,
                        barBorderRadius: 15
                    }
                }
            ],
            animationDuration: 0,
            animationDurationUpdate: 3000,
            animationEasing: 'linear',
            animationEasingUpdate: 'linear'
        };

        // 3. 把配置给实例对象
        myEcharts.setOption(option);
        // 4. 让图表跟随屏幕自动的去适应
        window.addEventListener("resize", function() {
            myEcharts.resize();
        });
        return myEcharts;
    }

    // 每日任务时间分配
    function initTaskTime(){
        tasksTime = echarts.init(document.querySelector('.tasksTime .chart'));
        let option = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // Use axis to trigger tooltip
                    type: 'shadow'        // 'shadow' as default; can also be 'line' or 'shadow'
                }
            },
            legend: {
                data: ['NODE', 'VUE', 'HTML5', 'JavaScript', 'Search Engine'],
                textStyle:{
                    color:'#fff'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type: 'value'
            },
            yAxis: {
                type: 'category',
                data: ['晚上','下午','上午'],
                axisLabel: {
                    color: "#fff"
                },
            },
            series: [
                {
                    name: 'NODE',
                    type: 'bar',
                    stack: 'total',
                    label: {
                        show: true
                    },
                    emphasis: {
                        focus: 'series'
                    },
                    data: [0.5, 1, 0.5]
                },
                {
                    name: 'VUE',
                    type: 'bar',
                    stack: 'total',
                    label: {
                        show: true
                    },
                    emphasis: {
                        focus: 'series'
                    },
                    data: [1,0.5,1]
                },
                {
                    name: 'HTML5',
                    type: 'bar',
                    stack: 'total',
                    label: {
                        show: true
                    },
                    emphasis: {
                        focus: 'series'
                    },
                    data: [0.5, 0.5, 0.5]
                },
                {
                    name: 'JavaScript',
                    type: 'bar',
                    stack: 'total',
                    label: {
                        show: true
                    },
                    emphasis: {
                        focus: 'series'
                    },
                    data: [1, 1.5, 2]
                },
                {
                    name: 'Search Engine',
                    type: 'bar',
                    stack: 'total',
                    label: {
                        show: true
                    },
                    emphasis: {
                        focus: 'series'
                    },
                    data: [1, 0.5, 0.5]
                }
            ]
        };
        tasksTime.setOption(option);
    }

    // 每日时间占比
    function initDailyTaskPresent() {
        dailyTasksPrecent = echarts.init(document.querySelector('.dailyTasksPrecent .chart'));
        let option = {
            tooltip: {
                trigger: 'item'
            },
            series: [
                {
                    name: '访问来源',
                    type: 'pie',
                    radius: '50%',
                    data: [
                        {value: 2, name: 'Node'},
                        {value: 1, name: 'Vue'},
                        {value: 5, name: 'HTML5'},
                        {value: 2, name: 'JavaScript'},
                        {value: 3, name: 'Search Engine'},
                        {value: 4, name: '日常'}
                    ],
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        dailyTasksPrecent.setOption(option);
    }

    // 与前日对比
    function initCompareYesterDay() {
        let myColor = ["#1089E7", "#F57474", "#56D0E3", "#F8B448", "#8B78F6"];
        compareWithYesterday = echarts.init(document.querySelector('.compareWithYesterday .chart'));
        let labelRight = {
            position: 'inside',
            color:'#fff'
        };
        let option = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                top: 0,
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type: 'value',
                position: 'top',
                splitLine: {
                    lineStyle: {
                        type: 'dashed'
                    }
                },
                axisLabel:{
                    textStyle:{
                        color:'#fff'
                    }
                }
            },
            yAxis: {
                type: 'category',
                axisLine: {show: false},
                axisLabel: {show: false},
                axisTick: {show: false},
                splitLine: {show: false},
                data: ['日常时间对比','任务总时长对比','任务完成率对比']
            },
            series: [
                {
                    name: '生活费',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        show: true,
                        formatter: '{b}'
                    },
                    data: [
                        {value: -0.07, label: labelRight},
                        {value: -0.09, label: labelRight},
                        {value: 0.23, label: labelRight}
                    ],
                    itemStyle:{
                        color:function (params) {
                            return myColor[params.dataIndex];
                        }
                    }
                }
            ]
        };
        compareWithYesterday.setOption(option);
    }

    // 每周任务
    function initWeekTasks() {
        weekTasks = taskItems('.weekTasks .chart');
    }

    //每月任务
    function initMonthTasks() {
        monthTasks = taskItems('.monthTasks .chart');
    }
});