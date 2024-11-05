FROM python:3.11
WORKDIR /app

RUN pip install Django psycopg2 djangorestframework drf-yasg

COPY . .
ENV PYTHONUNBUFFERED 1
CMD ["sh", "-c", "python manage.py migrate && python manage.py runserver 0.0.0.0:8000"]