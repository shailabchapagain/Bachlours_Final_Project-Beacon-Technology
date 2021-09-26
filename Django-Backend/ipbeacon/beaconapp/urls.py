from django.urls import path
from . import views


urlpatterns = [
    path("beacons/", views.BeaconList.as_view()),
    path("beacon/details/<int:pk>/", views.BeaconDetail.as_view()),

    # url for categories
    path("categoriesList/", views.CategoryList.as_view()),
    path("category/details/<int:pk>/", views.CategoryDetail.as_view()),

    # url for beacon position
    path("beaconPositions/", views.BeaconPositionList.as_view()),
    path("beacon/position/details/<int:pk>/", views.BeaconPositionDetail.as_view()),

    # url for information
    path("informationList/", views.InformationList.as_view()),
    path("information/details/<int:pk>/", views.InformationDetail.as_view())



]