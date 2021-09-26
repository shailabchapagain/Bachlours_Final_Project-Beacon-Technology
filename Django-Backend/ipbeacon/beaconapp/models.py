from django.db import models

# Create your models here.
class Beacon(models.Model):
    beaconID = models.AutoField(primary_key=True)
    beaconName = models.CharField(max_length=200)

    def __str__(self) -> str:
        return str(self.beaconID)

class Category(models.Model):
    categoryID = models.AutoField(primary_key=True)
    categoryName = models.CharField(max_length=50)

    def __str__(self):
        return self.categoryName


class BeaconPosition(models.Model):
    beaconPositionID = models.AutoField(primary_key=True)
    beaconID = models.ForeignKey(Beacon, on_delete=models.CASCADE)
    positionDescription = models.TextField(max_length=45)
    GPSCoordinates = models.CharField(max_length=45)
    categoryID = models.ForeignKey(Category, on_delete=models.CASCADE)

    def __str__(self) -> str:
        return str(self.beaconID)

class Information(models.Model):
    inforamtionID = models.AutoField(primary_key=True)
    informationText = models.TextField(max_length=45)
    beaconID = models.ForeignKey(Beacon, on_delete=models.CASCADE)
    datetime = models.DateTimeField(max_length=45)

    def __str__(self):
        return self.informationText
