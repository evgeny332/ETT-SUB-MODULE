'use strict';

// declare modules
angular.module('Authentication', []);
angular.module('myApp', []);
angular.module('postApp', []);
angular.module('BasicHttpAuthExample', [
    'Authentication',
    'myApp',
    'postApp',
    'ngRoute',
    'ngCookies'
])

.config(['$routeProvider','$locationProvider', function ($routeProvider, $locationProvider) {

    $routeProvider
        .when('/login', {
            controller: 'LoginController',
            templateUrl: '/ApiPanel/app/login.html'
        })
        .when('/dashboard', {
            controller: 'studentController',
            templateUrl: '/ApiPanel/app/admin.html'
        })
        .otherwise({ redirectTo: '/login' });

    //$locationProvider.html5Mode(true);
}])

.run(['$rootScope', '$location', '$cookieStore', '$http',
    function ($rootScope, $location, $cookieStore, $http) {
        // keep user logged in after page refresh

        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in
            if ($location.path() !== '/login' && !$rootScope.globals.currentUser) {
                $location.path('/login');
            }
        });
    }]);
