/**
 * Created by cbarne02 on 5/11/15.
 */

(function() {
    var app = angular.module("explorerServices");
    app.factory('newProject', function newProjectFactory() {
        //just return an empty object for now.
        return {};
    });

    app.service("projectService", ['$http', 'admin_api', 'newProject', function ($http, admin_api, newProject) {

        function appendTransform(defaults, transform) {
            // We can't guarantee that the default transformation is an array
            defaults = angular.isArray(defaults) ? defaults : [defaults];

            // Append the new transformation to the defaults
            return defaults.concat(transform);
        };

        this.findAll = function () {

            return $http({
                url: admin_api.projects,
                method: 'GET',
                transformResponse: appendTransform($http.defaults.transformResponse, function (value) {
                    return value._embedded.projects;
                })
            });
        };


        this.getHref = function (item, resourceName){
            var links = item._links;
            if (links.hasOwnProperty(resourceName)){
                return links[resourceName].href;
            }

            return null;
        };

        this.hasLink = function(item, resourceName){
            return (this.getHref(item, resourceName) !== null);
        };

        this.getResource = function(item, name){
            if (!this.hasLink(item, name)){
                throw new Error("No " + name + " for this project!");
            }
            return $http({
                url: this.getHref(item, name),
                method: 'GET'
            });
        }

        this.getCourse = function (item) {
            return this.getResource(item, 'course');
        };

        this.getPaper = function (item) {
            return this.getResource(item, 'paper');
        };

        this.getPoster = function (item) {
            return this.getResource(item, 'poster');
        };

        this.find = function (id) {
            return $http({
                url: admin_api.projects + '/' + id,
                method: 'GET'
            });
        }

        this.create = function (item) {
            if (typeof item === "undefined"){
                item = newProject;
            }
            return $http.post(admin_api.projects, item);
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