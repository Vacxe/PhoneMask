# PhoneMask
PhoneMask is lightweight android library for EditText formatting. Easy way for add phone readability in your project.


[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-PhoneMask-green.svg?style=true)](https://android-arsenal.com/details/1/5770)

## How to use it
In Kotlin Just use PhoneMaskManager class
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

You can also use this in Java,  
```java
 new PhoneMaskManager()
                .withMask(" (###) ###-##")
                .withRegion("+255")
                .bindTo(EditText)findViewById(R.id.text_edit_text))
```

### About methods
#### Init methods
- **withMask** 
(required field)
Init your mask format. Use `#` symbol by default

- **withMaskSymbol** 
(optional field)
Changing default symbol mask

- **withRegion**
(optional field)
Init your region

- **withValueListener**
(optional field)
If you want to receive callback from EditText just add ValueListener, and you receive phone string in clear format 
(For example: +70009199191)

- **withOnFocusChangeListener**
(optional field)
If you want to set OnFocusChangeListener for EditText use this method

- **bindTo** 
(required calling)
Afrer setup just call this method for binding to `EditText`

#### Get methods
- **getPhone** 
Return phone in clear format (For example: +70009199191)

### Download

```xml
<dependency>
  <groupId>com.github.vacxe</groupId>
  <artifactId>phonemask</artifactId>
  <version>1.0.5</version>
  <type>pom</type>
</dependency>
```
or Gradle:
```groovy

repositories {
    jcenter()
}

dependencies {
    compile 'com.github.vacxe:phonemask:1.0.5'
}
```
