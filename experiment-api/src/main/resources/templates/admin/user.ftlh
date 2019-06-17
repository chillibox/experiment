<#include "../include/admin/layout.ftlh"/>
<@layout active="user" navTitle="用户">
<div class="content" data-ng-app="app" data-ng-controller="UserCtrl">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="header">
                        <h4 class="title"></h4>
                        <p class="category"></p>
                        <div class="text-right">
                            <div class="row">
                                <div class="col-md-12">
                                    <a href="javascript:void(0)" class="btn btn-primary" title="新增用户"
                                       data-ng-click="create()"><i class="ti-file"></i>新增用户</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="content table-responsive table-full-width">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>用户名</th>
                                <th>邮箱</th>
                                <th>角色</th>
                                <th width="180">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr data-ng-repeat="u in users">
                                <td>{{u.username}}</td>
                                <td>{{u.email || '暂无邮箱'}}</td>
                                <td>{{u.role === 'admin' ? '管理员' : '员工'}}</td>
                                <td>
                                    <a href="javascript:void(0)" class="btn btn-success" title="编辑"
                                       data-ng-click="edit(u)"><i class="ti-pencil"></i></a>
                                    <a href="javascript:void(0)" class="btn btn-danger" title="删除"
                                       data-ng-click="del(u.id)"><i class="ti-trash"></i></a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</@layout>