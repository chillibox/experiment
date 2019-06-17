<#include "../include/admin/layout.ftlh"/>
<@layout active="" navTitle="个人信息和密码">
<div class="content" data-ng-app="app" data-ng-controller="ProCtrl">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="header">
                        <h4 class="title"></h4>
                        <p class="category"></p>
                    </div>
                    <div class="content">
                        <div data-ng-show="user">
                            <form name="userForm">
                                <div class="modal-body" id="modal-body">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label>用户名</label>
                                                <input type="text" class="form-control border-input"
                                                       value="{{user.username}}" readonly>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label>邮箱</label>
                                                <input type="email" class="form-control border-input"
                                                       data-ng-model="user.email">
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label>角色</label>
                                                <input type="text" class="form-control border-input"
                                                       value="{{u.role === 'admin' ? '管理员' : '员工'}}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label>密码</label>
                                                <input type="password" class="form-control border-input"
                                                       data-ng-model="user.password">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label>确认密码</label>
                                                <input type="password" class="form-control border-input"
                                                       data-ng-model="pc.passwordConfirm">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button class="btn btn-primary" type="submit" data-ng-click="save()">确定</button>
                                </div>
                            </form>
                        </div>
                        <div data-ng-if="!user" class="text-center">
                            <h3>无法修改用户信息和密码</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</@layout>