/**
 * Created by cbarne02 on 5/11/15.
 */

(function() {



    var services = angular.module("explorerServices", []);

    services.factory('admin_api', ["profile", function(profile){
        // inject a profile object here that points to test data for testing and prod data for prod
        var test_api = {
            courses: '/test_data/testcourses.json'
        };
        var prod_api = {
            courses: '/api/courses',
            projects: '/api/projects'
        };

        var api = {};

        switch (profile){
            case 'prod':
            case 'dev':
                api = prod_api;
                break;
            case 'test':
                api = test_api;
                break;
            default:
                throw new Error("unrecognized profile ['" + profile + "']");

        }

        return api;

    }]);

})();