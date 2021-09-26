from rest_framework import serializers
from .models import *

# beacon class serializer
class BeaconSerializer(serializers.ModelSerializer):
    class Meta:
        model = Beacon
        fields = "__all__"

# category class serializer
class CategorySerializer(serializers.ModelSerializer):
    class Meta:
        model = Category
        fields = "__all__"    

# beacon position class serializer
class BeaconPositionSerializer(serializers.ModelSerializer):
    class Meta:
        model = BeaconPosition
        fields = "__all__"

# information class serializer
class InformationSerializer(serializers.ModelSerializer):
    class Meta:
        model = Information
        fields = "__all__"
