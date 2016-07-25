'use strict';

angular.module('postApp', [])
    .controller('postController', function($scope, $http) {

    $scope.user = {};
    $scope.goLive = function (data) {

        //alert(data);
        if(data == 0){
            $scope.user.require = 'start';
        }else if (data == 1){
            $scope.user.require = 'stop';
        }
        alert(data);

        var urlto = "http://54.209.220.78:8888/earntalktime/api/ads/update/";

        //alert(urlto);
        $scope.closeThisDialog();
        $http({
            method  : 'POST',
            url     : urlto,
            data    : $scope.user,
            transformRequest: function(obj) {
                var str = [];
                for(var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
            headers : {'Content-Type': 'application/x-www-form-urlencoded'}
        })
            .success(function(data) {
                alert("Success");
            })
            .error(function(data){
                alert("Failure");
        });
    }
});

angular.module('myApp', ['ngDialog', 'tableSort'])

    //.config(['ngDialogProvider', function (ngDialogProvider){
    //    ngDialogProvider.setDefaults({
    //        className: 'ngdialog-theme-default',
    //        plain: true,
    //        showClose: true,
    //        closeByDocument: true,
    //        closeByEscape: true
    //    });
    //}])

    .controller('studentController', function ($scope, $http, $location, $window, $sce, ngDialog) {

        $scope.tab = localStorage.getItem("tab");
        //alert($scope.tab);
        if($scope.tab == null){
            $scope.tab = 1;
            localStorage.setItem("tab", $scope.tab);
        }

        var url = "http://108.161.133.119/LiveOfferResponseAPI/OfferResponse?isIncent=1";
        var url1 = "http://108.161.133.119/LiveOfferResponseAPI/OfferResponse?isIncent=0";

        $http.defaults.headers.post["Content-Type"] = "text/plain";
        $http.get(url).success(function (response) {
            $scope.datas = response;
            $scope.totalItems = $scope.datas.offers;
            $scope.numPerPage = $scope.totalItems.length;
            $scope.noOfPages = Math.ceil($scope.totalItems.length / $scope.numPerPage);
        });

        $http.get(url1).success(function (response) {
            $scope.nonincent = response;
        });

        $scope.changeTab = function (data){
            $scope.tab = data;
            localStorage.setItem("tab", $scope.tab);
        };

        $scope.openPopup = function (data) {
            ngDialog.open({
                template: 'modalPopup.html',
                data: data
            });
        };

        $scope.takeLive = function (data) {
            ngDialog.open({
                template: 'LivePopup.html',
                data: data
            });
        };

        $scope.getNumber = function () {
            return new Array($scope.noOfPages);
        }

        $scope.changePage = function (num) {
            if ($scope.noOfPages >= num && num >= 1) {
                $scope.currentPage = num;
            }
            //getNumber();
        }
        //$scope.totalItems = $scope.datas.offers;
        $scope.currentPage = 1;

        $scope.paginate = function (value) {
            var begin, end, index;
            begin = ($scope.currentPage - 1) * $scope.numPerPage;
            end = begin + $scope.numPerPage;
            index = $scope.datas.offers.indexOf(value);
            return (begin <= index && index < end);
        };

        $scope.filtera = function (value) {
            $scope.noOfPages = Math.ceil($scope.totalItems.length / $scope.numPerPage);
            $scope.paginate(value);
        };

        $scope.reference = [
            {
                "ApiName": "IO Display",
                "OfferName": true,
                "Payout": true,
                "Description": true,
                "ImageUrl": true,
                "ActionUrl": true,
                "Mode": true,
                "TotalCap": false,
                "DailyCap": false,
                "PackageName": true,
                "PlayStoreUrl": true,
                "Countries": true
            },
            {
                "ApiName": "Persona",
                "OfferName": true,
                "Payout": true,
                "Description": true,
                "ImageUrl": true,
                "ActionUrl": true,
                "Mode": false,
                "TotalCap": false,
                "DailyCap": false,
                "PackageName": true,
                "PlayStoreUrl": false,
                "Countries": false
            },
            {
                "ApiName": "SupersonicAds",
                "OfferName": true,
                "Payout": true,
                "Description": true,
                "ImageUrl": true,
                "ActionUrl": true,
                "Mode": true,
                "TotalCap": false,
                "DailyCap": false,
                "PackageName": false,
                "PlayStoreUrl": false,
                "Countries": true
            },
            {
                "ApiName": "MiniMob",
                "OfferName": true,
                "Payout": true,
                "Description": true,
                "ImageUrl": true,
                "ActionUrl": true,
                "Mode": true,
                "TotalCap": true,
                "DailyCap": true,
                "PackageName": true,
                "PlayStoreUrl": true,
                "Countries": true
            },
            {
                "ApiName": "ClicksMob",
                "OfferName": true,
                "Payout": true,
                "Description": true,
                "ImageUrl": true,
                "ActionUrl": true,
                "Mode": true,
                "TotalCap": true,
                "DailyCap": true,
                "PackageName": true,
                "PlayStoreUrl": false,
                "Countries": true
            },
            {
                "ApiName": "Crunchiemedia",
                "OfferName": true,
                "Payout": true,
                "Description": true,
                "ImageUrl": true,
                "ActionUrl": true,
                "Mode": true,
                "TotalCap": false,
                "DailyCap": false,
                "PackageName": false,
                "PlayStoreUrl": false,
                "Countries": true
            },
            {
                "ApiName": "MappStreet",
                "OfferName": true,
                "Payout": true,
                "Description": true,
                "ImageUrl": true,
                "ActionUrl": true,
                "Mode": false,
                "TotalCap": false,
                "DailyCap": true,
                "PackageName": true,
                "PlayStoreUrl": true,
                "Countries": true
            },
        ];
    })

    .filter('cut', function () {
        return function (value, wordwise, max, tail) {
            if (!value) return '';

            max = parseInt(max, 10);
            if (!max) return value;
            if (value.length <= max) return value;

            value = value.substr(0, max);
            if (wordwise) {
                var lastspace = value.lastIndexOf(' ');
                if (lastspace != -1) {
                    value = value.substr(0, lastspace);
                }
            }
            return value + (tail || ' …');
        };
    })

    .filter('true_false', function () {
        return function (text, length, end) {
            var txt = document.createElement("textarea");
            if (text) {
                txt.innerHTML = '&#x2714;';
                return txt.value;
            }
            txt.innerHTML = '&#x2717;';
            return txt.value;
        }
    })

    .filter('enttoChar', function ($sce) {
        return function (text) {
            var txt = document.createElement("textarea");
            txt.innerHTML = text;
            return $sce.trustAsHtml(txt.value);
        }
    })

    .filter('incent', function ($sce) {
        return function (text) {
            if(text == 0){
                return 'Non-Incent';
            }else if(text == 1){
                return 'Incent';
            }else if(text == 2){
                return 'Both';
            }
            return 'N.A.';
        }
    })

    .filter('titleCase', function () {
        return function (input) {
            input = input || '';
            return input.replace(/\w\S*/g, function (txt) {
                return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
            });
        };
    })

    .filter('split', function () {
        return function (input, splitChar, splitIndex) {
            // do some bounds checking here to ensure it has that index
            return input.split(splitChar)[splitIndex];
        }
    });