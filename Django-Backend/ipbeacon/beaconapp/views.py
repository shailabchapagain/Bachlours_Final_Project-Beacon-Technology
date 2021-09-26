from django.db.models import manager
from django.shortcuts import render
from django.http import Http404
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .models import *
from .serializers import *

# Create your views here.
class BeaconList(APIView):

    def get(self, request, format=None):
        beacons = Beacon.objects.all()
        serializer = BeaconSerializer(beacons, many=True)
        return Response(serializer.data)


    def post(self, request, format=None):
        serializer = BeaconSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

# beacon details
class BeaconDetail(APIView):

    def get_object(self, pk):
        try:
            beacon = Beacon.objects.get(pk=pk)
            return beacon
        except Beacon.DoesNotExist:
            raise Http404

    def get(self, request, pk, format=None):
        beacon = self.get_object(pk)
        serializer = BeaconSerializer(beacon)
        return Response(serializer.data)

    def put(self, request, pk, format=None):
        beacon = self.get_object(pk)
        serializer = BeaconSerializer(beacon, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, pk, format=None):
        beacon = self.get_object(pk)
        beacon.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)



# cagegory list
class CategoryList(APIView):

    def get(self, request):
        categories = Category.objects.all()
        serializer = CategorySerializer(categories, many=True)
        return Response(serializer.data)

    def post(self, request, format=None):
        serializer = CategorySerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

# category detail view
class CategoryDetail(APIView):
    # get category instance
    def get_object(self, pk):
        try:
            category = Category.objects.get(pk=pk)
            return category
        except Beacon.DoesNotExist:
            raise Http404

    def get(self, request, pk, format=None):
        category = self.get_object(pk)
        serializer = CategorySerializer(category)
        return Response(serializer.data)

    def put(self, request, pk, format=None):
        category = self.get_object(pk)
        serializer = CategorySerializer(category, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, pk, format=None):
        category = self.get_object(pk)
        category.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)    


# api view set for beacon position
class BeaconPositionList(APIView):

    def get(self, request, format=None):
        beacons = BeaconPosition.objects.all()
        serializer = BeaconPositionSerializer(beacons, many=True)
        return Response(serializer.data)

    def post(self, request, format=None):
        beaconPosition = BeaconSerializer(data=request.data)
        if beaconPosition.is_valid():
            beaconPosition.save()
            return Response(beaconPosition.data, status=status.HTTP_201_CREATED)
        return Response(beaconPosition.errors, status=status.HTTP_400_BAD_REQUEST)

# api detail view for beacon positions
class BeaconPositionDetail(APIView):
    def get_object(self, pk):
        try:
            position = BeaconPosition.objects.get(pk=pk)
            return position
        except Beacon.DoesNotExist:
            raise Http404

    def get(self, request, pk, format=None):
        position = self.get_object(pk)
        serializer = BeaconPositionSerializer(position)
        return Response(serializer.data)

    def put(self, request, pk, format=None):
        position = self.get_object(pk)
        serializer = BeaconPositionSerializer(position, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, pk, format=None):
        position = self.get_object(pk)
        position.delete()
        return Response(status=status.HTTP_204_NO_CONTENT) 

# api set for information
class InformationList(APIView):
    def get(self, request, format=None):
        information = Information.objects.all()
        serializer = InformationSerializer(information, many=True)
        return Response(serializer.data)

    def post(self, request, format=None):
        serializer = InformationSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
        
# api view set for information details
class InformationDetail(APIView):
    def get_object(self, pk):
        try:
            information = Information.objects.get(pk=pk)
            return information
        except Beacon.DoesNotExist:
            raise Http404

    def get(self, request, pk, format=None):
        information = self.get_object(pk)
        serializer = InformationSerializer(information)
        return Response(serializer.data)

    def put(self, request, pk, format=None):
        information = self.get_object(pk)
        serializer = InformationSerializer(information, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, pk, format=None):
        information = self.get_object(pk)
        information.delete()
        return Response(status=status.HTTP_204_NO_CONTENT) 