/**
 * Created by cbarne02 on 5/11/15.
 */

(function() {

    angular.module("explorerControllers").controller("ProjectsCtrl", ['$scope', '$location',
        '$modal', 'projectService',
        function ($scope, $location, $modal, apiService) {

        $scope.config = {
            service: "projectService",
            modal_form: {
                item_label: "Project",
                templateUrl: 'templates/addProject.html',
                controller_name: "ProjectFormCtrl",
                columnDefs: [
                    {
                        name: 'poster',
                        cellTemplate: '<div><img src="{{row.entity.poster.thumbUrl}}"></div>'
                    },

                    {name: 'title'},
                    {
                        name: 'course', width: 100,
                        cellTemplate: '<div>{{row.entity.course.name}} | {{row.entity.course.instructor}}</div>'
                    },
                    {
                        name: 'year', width: 100,
                        cellTemplate: '<div>{{row.entity.course.semester}} {{row.entity.course.year}}</div>'
                    },
                    {name: 'searchable', width: 50}]
            }
        };

        //options for ui.grid
        $scope.gridOptions = {};

        var columnDefs = [
            {
                name: 'edit',
                width: 100,
                cellTemplate: '<button class="btn btn-default btn-sm" ng-click="grid.appScope.editItem(row.entity)">edit</button>'
            },
            {
                name: 'delete',
                width: 100,
                cellTemplate: '<button class="btn btn-danger btn-sm" ng-click="grid.appScope.deleteItem(row.entity)">X</button>'
            }
        ];

        $scope.gridOptions.columnDefs = $scope.config.modal_form.columnDefs.concat(columnDefs);


            $scope.gotoProject = function(location){
                //route to edit page with Location
                var projectId = location.substr(location.lastIndexOf("/") + 1);
                $location.path('/projects/' + projectId);


            }



        $scope.editItem = function (item) {
            $scope.openForm('edit', item).then(function (result) {
                console.log(result); //error handling?
                //on modal dismiss, item list should update
                apiService.update(result.item).success(function (data) {
                    console.log(data);
                    $scope.getItems();
                });

            }, function () {
                console.log('Modal dismissed at: ' + new Date());
            });
        };

        $scope.createItem = function () {

                apiService.create().success(function (data, status, header, config) {
                    //Location:http://localhost:8080/api/projects/3
                    var itemRef = header("Location");
                    $scope.gotoProject(itemRef);

                });

        };

        $scope.deleteItem = function (item) {
            apiService.delete(item).success(function (data) {
                console.log(data);
                $scope.getItems();
            });
        };

        $scope.getItems = function () {
            apiService.findAll().success(function (response) {
                //callback from the service populates the ui-grid data array
                $scope.gridOptions.data = response;
                $scope.populateResources();
            });
        };

        $scope.populateResources = function () {
            for (var i in $scope.gridOptions.data) {
                var item = $scope.gridOptions.data[i];
                $scope.populateCourse(item);
                $scope.populatePoster(item);
            }
        };

            $scope.populateCourse = function(item){
                if (!apiService.hasLink(item, 'course')){
                    return;
                }
                apiService.getCourse(item).success(function (response) {
                    item.course = response;
                }).error(function(){
                    item.course = {};
                });
            };

            $scope.populatePoster = function(item){
                if (!apiService.hasLink(item, 'poster')){
                    return;
                }
                apiService.getPoster(item).success(function (response) {
                    console.log(response);
                    item.poster = {};
                    item.poster.thumbUrl = response._links.preview.href;
                }).error(function(){
                    item.poster = {};
                });
            };

        $scope.formText = {
            display: {},
            edit: {
                header: "Edit " + $scope.config.modal_form.item_label
            },
            create: {
                header: "Create a New " + $scope.config.modal_form.item_label
            }
        };

        //initial population of list
        $scope.getItems();

        $scope.openForm = function (mode, item) {
            $scope.mode = mode;
            $scope.editedItem = {}; //initialize the object for editing
            if (mode === 'edit') {
                //we need to inject the course values into the form ( a copy of the object )
                if (typeof item !== "undefined") {
                    $scope.currentItem = item;
                    angular.copy(item, $scope.editedItem);
                } else {
                    throw new Error("'item' parameter missing!");
                }

            } else if (mode === 'create') {
                $scope.currentItem = {};
            }

            $scope.formText.display.header = $scope.formText[mode].header;


            $scope.modalInstance = $modal.open({
                templateUrl: $scope.config.modal_form.templateUrl,
                controller: $scope.config.modal_form.controller_name,
                scope: $scope
            });


            return $scope.modalInstance.result;
        };


        //buttons
        $scope.save = function () {
            //do some validation? test to see if edited course differs from current course?
            $scope.modalInstance.close({success: true, item: $scope.editedItem});

        };

        $scope.reset = function () {
            $scope.editedItem = {};
        };

        $scope.cancel = function () {
            $scope.modalInstance.dismiss('cancelled');
        };

    }]);

})();