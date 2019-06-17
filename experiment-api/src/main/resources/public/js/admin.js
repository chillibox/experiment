$(function(){
    bootbox.setLocale('zh_CN');

    window.notify = {
        info: function (message){
            $.notify({
                message: message
            },{
              type: 'info',
              z_index: 9999
            });
        },
        warning: function (message){
            $.notify({
                message: message
            },{
              type: 'warning',
              z_index: 9999
            });
        },
        danger: function (message){
            $.notify({
                message: message
            },{
              type: 'danger',
              z_index: 9999
            });
        },
    };
});

function locationOrgin() {
    return document.location.protocol+'//'
    + document.location.hostname + (document.location.port ? ':' + document.location.port : '');
}

function timeSharpBegin(time) {
    return moment(time).hour(0).minute(0).second(0).millisecond(0).valueOf();
}

function timeSharpEnd(time) {
    return moment(time).hour(23).minute(59).second(59).millisecond(0).valueOf();
}

function nowOffsetDay(offset, doffset) {
    return timeOffsetDay(new Date(), offset, doffset)
}

function timeOffsetDay(time, offset, doffset) {
    if(doffset === undefined) doffset = 0;
    offset = (offset === undefined || offset === null) ? doffset : offset;
    return moment(time).add(offset, 'days').valueOf();
}

function extend(a, b) {
    if(!a) a = {};
    if(!b) b = {};
    return angular.extend(a, b);
}

var app = angular.module('app', ['ui.bootstrap', 'cfp.hotkeys', 'ui.select', 'ngSanitize', 'ui.bootstrap.datetimepicker']);
var csrfHeader = {};

app.factory('authInterceptor', function() {
    return {
        'responseError': function(resp) {
            if(resp.status === 401) {
                document.location.reload();
            }
            if(resp.status === 403) {
                document.location.href = ctx + '/admin/dashboard';
            }
        }
    };
});

app.config(function($httpProvider) {
    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
    csrfHeader[$("meta[name='_csrf_header']").attr("content")] = $("meta[name='_csrf']").attr("content");
    $httpProvider.defaults.headers.post = extend($httpProvider.defaults.headers.post, csrfHeader);
    $httpProvider.defaults.headers.delete = extend($httpProvider.defaults.headers.delete, csrfHeader);
    $httpProvider.defaults.headers.put = extend($httpProvider.defaults.headers.put, csrfHeader);

    $httpProvider.interceptors.push('authInterceptor');
});

app.run(function($rootScope) {
    moment.locale('zh-cn');
    $rootScope.const = {
        ctx: window.ctx,
        colors: ["#a9d92d", "#63b359", "#2c9f67", "#509fc9", "#5885cf", "#9062c0", "#d09a45", "#e4b138", "#ee903c", "#f08500", "#dd6549", "#cc463d", "#cf3e36", "#5E6671"]
    };
});

app.directive('bgColor', function() {
    return {
        restrict: 'A',
        scope: {
            bgColor: '='
        },
        link: function(scope, element, attrs) {
            scope.$watch('bgColor', function(nv, ov) {
                if(!nv)
                    nv = '#63b359';
                attrs.$set('style', 'background-color:' + nv);
            });
        }
    };
});

app.directive('convertToNumber', function() {
  return {
    require: 'ngModel',
    link: function(scope, element, attrs, ngModel) {
      ngModel.$parsers.push(function(val) {
        return parseInt(val, 10);
      });
      ngModel.$formatters.push(function(val) {
        return '' + val;
      });
    }
  };
});

app.directive('convertToBoolean', function() {
  return {
    require: 'ngModel',
    link: function(scope, element, attrs, ngModel) {
      ngModel.$parsers.push(function(val) {
        return (val === 'false') ? false : true;
      });
      ngModel.$formatters.push(function(val) {
        return val ? 'true' : 'false';
      });
    }
  };
});

app.factory('pageHotkeys', function(hotkeys) {
    return function($scope) {
        hotkeys.bindTo($scope)
            .add({
                combo: 'ctrl+right',
                description: '下一页',
                callback: function() {
                    $scope.currentPage = $scope.currentPage < $scope.totalPages ?
                                    $scope.currentPage + 1 : $scope.currentPage;
                    $scope.pageChanged();
                }
            })
            .add({
                combo: 'ctrl+left',
                description: '上一页',
                callback: function() {
                    $scope.currentPage = $scope.currentPage > 1 ?
                                $scope.currentPage - 1 : $scope.currentPage;
                    $scope.pageChanged();
                }
            });
    };
});

app.controller('UserCtrl', function($scope, $uibModal, userService) {

    $scope.resolve = function (resp) {
        $scope.users = resp.data;
    };

    $scope.changed = function () {
        userService.first().then($scope.resolve);
    }

    $scope.changed();

    $scope.del = function(id) {
        bootbox.confirm('确定删除吗？', function(r) {
            if(!r) return;

            userService.del(id).then(function() {
                notify.info('删除成功');
                $scope.changed();
            },function(){
                notify.danger('删除失败');
            });
        });
    };

    $scope.create = function() {
        $uibModal.open({
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: templateUrl('userForm'),
            controller: 'UserFrmCtrl',
            size: 'lg',
            resolve: {
                user: function() {
                    return {role: 'staff'};
                },
                update: function() {
                    return $scope.changed;
                }
            }
        });
    };

    $scope.edit = function(user) {
        $uibModal.open({
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: templateUrl('userForm'),
            controller: 'UserFrmCtrl',
            size: 'lg',
            resolve: {
                user: function() {
                    return user;
                },
                update: function() {
                    return $scope.changed;
                }
            }
        });
    };
});

app.controller('UserFrmCtrl', function($scope, user, update, userService, $uibModalInstance){
    if(!user || !user.id)
        $scope.isSave = true;

    $scope.user = user;
    $scope.pc = {};

    $scope.close = function() {
        $uibModalInstance.dismiss('cancel');
    };

    $scope.save = function() {
        if(!$scope.userForm.$valid) return;
        if($scope.user.password !== $scope.pc.passwordConfirm) {
            notify.warning('密码不一致');
            return;
        }

        function saveSuccesful(resp) {
            notify.info('保存成功');
            $uibModalInstance.close();
        }

        function saveFailed(resp) {
            if(resp.status === 409) {
                notify.danger('用户名重复或和默认管理员用户名重复');
                return;
            }
            notify.danger('保存失败');
        }

        userService.save($scope.user).then(saveSuccesful, saveFailed);
    }

    $uibModalInstance.result.then(function (r) {
        update();
    }, function () {
        update();
    });
});

app.controller('ProCtrl', function($scope, $http) {
    function resolve(resp) {
        $scope.user = resp.data;
        $scope.pc = {};
    }

    $scope.pc = {};
    $scope.user = {};

    $scope.changed = function () {
        $http.get(ctx + '/admin/api/profile').then(resolve, function(){
            $scope.user = undefined;
            $scope.pc = {};
        });
    };

    $scope.changed();

    $scope.save = function() {
        if(!$scope.userForm.$valid) return;
        if($scope.user.password !== $scope.pc.passwordConfirm) {
            notify.warning('密码不一致');
            return;
        }

        function saveSuccesful(resp) {
            notify.info('保存成功');
            $scope.changed();
        }

        function saveFailed() {
            notify.danger('保存失败');
            $scope.changed();
        }

        $http.post(ctx + '/admin/api/profile', $scope.user).then(saveSuccesful, saveFailed);
    };
});
