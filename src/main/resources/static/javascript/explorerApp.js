/**
 * Created by cbarne02 on 4/26/15.
 */

(function() {


    /**
     * bootstrap the application with config
     * @type {module}
     */
    var app = angular.module("explorerAdminApp", [
        'ngRoute',
        'ui.bootstrap',

        'explorerControllers'
    ]);

    //'test', 'dev', or 'prod'
    app.value('profile', 'dev');

    app.config(function($httpProvider, $locationProvider) {
        //help out Spring
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

        $locationProvider.html5Mode(true);

    });

    //we can specify the controller here
    app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/projects', {
            templateUrl: 'templates/projects.html',
            controller: 'ProjectsCtrl'
        });

        $routeProvider.when('/projects/:projectId', {
            templateUrl: 'templates/editProject.html',
            controller: 'EditProjectsCtrl'
        });


        $routeProvider.when('/courses', {
            templateUrl: 'templates/courses.html',
            controller: 'CoursesCtrl'
        });

        $routeProvider.otherwise({redirectTo: '/projects'});
    }]);


})();