from django.contrib import admin

from accounts.models import Account
from django.contrib.auth.admin import UserAdmin
# Register your models here.

class AccountAdmin(UserAdmin):
    list_display=('email','username','YourNumberinSchool','is_admin')
    search_fields=('email','YourNumberinSchool')
    readonly_fields=('date_joined','last_login')
    filter_horizontal=()
    list_filter=()
    fieldsets=()

admin.site.register(Account,AccountAdmin)

