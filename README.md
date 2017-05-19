# PhoneMask
PhoneMask is lightweight android library for EditText formatting. Easy way for add phone readability in your project.


[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-PhoneMask-green.svg?style=true)](https://android-arsenal.com/details/1/5770)

## How to use it
Just use PhoneMaskManager class
```kotlin
 PhoneMaskManager()
                .withMask(" (###) ###-##-##")
                .withRegion("+7")
                .withValueListener(object : ValueListener{
                    override fun onPhoneChanged(phone: String) {
                       
                    }
                })
                .bindTo((findViewById(R.id.text_edit_text) as EditText))
```

### About methods
- withMask 
(required field)
Init your mask format. Use `#` symbol by default

- withRegion
(optional field)
Init your region

- withValueListener 
(optional field)
If you want to receive callback from EditText just add ValueListener, and you receive phone string in clear format 
(For example: +70009199191)

- bindTo 
(required calling)
Afrer setup just call this method for binding to `EditText`

### Download

```xml
<dependency>
  <groupId>com.github.vacxe</groupId>
  <artifactId>phonemask</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```
or Gradle:
```groovy

repositories {
    jcenter()
}

dependencies {
    compile 'com.github.vacxe:phonemask:1.0.1'
}
```
