from django.db import models

from django.contrib.auth.models import AbstractBaseUser,BaseUserManager
from django.conf import settings
from django.db.models.signals import post_save
from django.dispatch import receiver
from rest_framework.authtoken.models import Token

# Create your models here.

# user:admin
#password:admin

class MyAccountManager(BaseUserManager):
    def create_user(self,email,username,YourNumberinSchool,password=None):
        if not email:
            raise ValueError("Users must have an email address")
        if not username:
            raise ValueError("Users must have an username")
        if not YourNumberinSchool:
            raise ValueError("Users must have an number registered in school")

        user = self.model(
            email=self.normalize_email(email),
            username=username,
            YourNumberinSchool=YourNumberinSchool,


        )

        user.set_password(password)
        user.save(using=self._db)
        return user

    def create_superuser(self,email,username,YourNumberinSchool,password):
        user = self.create_user(
            email=self.normalize_email(email),
            username=username,
            YourNumberinSchool=YourNumberinSchool,
            password=password
        )
        user.is_admin=True
        user.is_staff=True
        user.is_superuser=True
        user.save(using=self._db)
        return user


class Account(AbstractBaseUser):
    email       =models.EmailField(verbose_name="email",max_length=60,unique=True)
    username    =models.CharField(max_length=30,unique=True)
    phoneNumber=models.CharField(max_length=15)
    YourNumberinSchool=models.CharField(max_length=15)
    name=models.CharField(max_length=20)
    date_joined=models.DateTimeField(verbose_name='date joined',auto_now_add=True)
    last_login=models.DateTimeField(verbose_name='last login',auto_now=True) 
    is_admin = models.BooleanField(default=False)
    is_active = models.BooleanField(default=True)
    is_staff=models.BooleanField(default=False)
    is_superuser=models.BooleanField(default=False)

    USERNAME_FIELD='email'
    REQUIRED_FIELDS=['username','YourNumberinSchool']


    objects=MyAccountManager()

    def __str__(self) -> str:
        return self.email

    def has_perm(self,perm,obj=None):
        return self.is_admin
        
    def has_module_perms(self,app_level):
        return True


@receiver(post_save, sender=settings.AUTH_USER_MODEL)
def create_auth_token(sender, instance=None,created=False,**kwargs):
    if created:
        Token.objects.create(user=instance)