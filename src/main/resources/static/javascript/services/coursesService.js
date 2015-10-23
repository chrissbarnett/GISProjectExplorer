/**
 * Created by cbarne02 on 5/11/15.
 */

(function() {

    angular.module("explorerServices").service("courseService", ['$http', 'admin_api', function ($http, admin_api) {
        this.findAll = function () {

            function appendTransform(defaults, transform) {
                // We can't guarantee that the default transformation is an array
                defaults = angular.isArray(defaults) ? defaults : [defaults];

                // Append the new transformation to the defaults
                return defaults.concat(transform);
            }

            return $http({
                url: admin_api.courses,
                method: 'GET',
                transformResponse: appendTransform($http.defaults.transformResponse, function (value) {
                    return value._embedded.courses;
                })
            });
        };

        this.create = function (item) {
            return $http.post(admin_api.courses, item);
        };

        this.delete = function (item) {
            //I think we can use the link value to do the delete correctly
            console.log("deleting item at: " + item._links.self.href);
            return $http.delete(item._links.self.href);
        };

        this.update = function (item) {
            console.log("saving edits!")
            return $http.put(item._links.self.href, item);
        };

    }]);

})();