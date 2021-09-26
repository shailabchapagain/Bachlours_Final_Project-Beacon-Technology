# Generated by Django 2.0 on 2021-05-31 20:40

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Beacon',
            fields=[
                ('beaconID', models.AutoField(primary_key=True, serialize=False)),
                ('beaconName', models.CharField(max_length=200)),
            ],
        ),
        migrations.CreateModel(
            name='BeaconPosition',
            fields=[
                ('beaconPositionID', models.AutoField(primary_key=True, serialize=False)),
                ('positionDescription', models.TextField(max_length=45)),
                ('GPSCoordinates', models.CharField(max_length=45)),
                ('beaconID', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='beaconapp.Beacon')),
            ],
        ),
        migrations.CreateModel(
            name='Category',
            fields=[
                ('categoryID', models.AutoField(primary_key=True, serialize=False)),
                ('categoryName', models.CharField(max_length=50)),
            ],
        ),
        migrations.CreateModel(
            name='Information',
            fields=[
                ('inforamtionID', models.AutoField(primary_key=True, serialize=False)),
                ('informationText', models.TextField(max_length=45)),
                ('datetime', models.DateTimeField(max_length=45)),
                ('beaconID', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='beaconapp.Beacon')),
            ],
        ),
        migrations.AddField(
            model_name='beaconposition',
            name='categoryID',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='beaconapp.Category'),
        ),
    ]
