# Building-a-Tomcat


## Intro
After working on building many websites, I had been curious about how Tomcat actually works. From building this Tomcat, many features I was using but did not understand, incluidng Request and its methods, Session, Cookie and etc.,
get clearer, and it doesn't feel like a "black box" to me anymore. Although this is a simplified version of Tomcat, and by implementing it with the tutorial  would not make me understand everything thoroughly, 
it is definitely a good start, and would help me in reading the source code of Tomcat in the future.

## How to run
To start this customized Tomcat, simply start startup.bat

# Features
## log
![1](https://user-images.githubusercontent.com/50982809/130361950-6fed3c54-0da2-473b-a3f7-20adbc00fa68.png)


## It supports multiple webapps

![image](https://user-images.githubusercontent.com/50982809/130362258-f97570ab-5824-4930-9f67-9367332d7553.png)
![image](https://user-images.githubusercontent.com/50982809/130362335-d0f915da-e9ed-407b-b5c6-f9f972fb47f0.png)

## 404 Error in Tomcat style
![404](https://user-images.githubusercontent.com/50982809/130362372-72d6a0b1-8a86-48e9-85cd-716bc7a359b0.png)

## 500 Error
![500](https://user-images.githubusercontent.com/50982809/130362434-74266619-17b9-4e5d-ab4d-50e87b214d37.png)


## Different mime-types

### txt
![mime-type1](https://user-images.githubusercontent.com/50982809/130362472-919e203c-bc79-424c-b919-7794a736d27f.png).

### pdf
![mime-type2](https://user-images.githubusercontent.com/50982809/130362544-ac9dfd06-296f-49c5-b656-9deaf5a0167a.png).

### jpeg
![mime-type3](https://user-images.githubusercontent.com/50982809/130362550-03cadc83-e521-4cd9-9848-b7a2c198889b.png)


## Cookies
### setCookie
![setCookie](https://user-images.githubusercontent.com/50982809/130362637-fd0d8962-dbf0-4ab0-bfc8-334efbd50b60.png)
### getCookie
![getCookie](https://user-images.githubusercontent.com/50982809/130362649-e2bc23b8-c7a4-4b45-9810-f3655fdb6e4f.png)


## Other features implemented
- Host, engine, service, server implementation
- Multiply ports (connectors)
- Servlet
  - invokeServlet
  - defaultServlet
- ClassLoader
  - Common Class Loader
  - Webapp Class Loader
- redirect
- Filter
- Listener
- Session

## Resource
Based on the tutorial from https://how2j.cn
