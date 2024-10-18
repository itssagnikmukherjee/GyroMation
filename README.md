# Comic-like Effect with Gyroscopic Sensor 📱🎨

## Overview

This project demonstrates the creation of a **comic-like effect** using the gyroscopic sensor in mobile devices. It allows users to interact with superheroes displayed on the screen by tilting their device, creating dynamic movement and engaging animations, just like in comics!

## 🎬 Demo

Check out this demo of the comic-like effect in action!  

## 📲 Permissions

```xml
<uses-feature android:name="android.hardware.sensor.gyroscope" />
```
## 🎨 Customization

```kotlin
override fun onSensorChanged(event: SensorEvent?) {
                if (event != null) {
                    val deltaX = event.values[0] // adjust rotation around x-axis
                    val deltaY = event.values[1] // adjust rotation around y-axis

                    offsetX += deltaX * 10 // Adjust sensitivity
                    offsetY += deltaY * 10 // Adjust sensitivity
                }
            }
```

## ✨ Features
- **Interactive Tilt Animations:** Using the gyroscope sensor, the superhero images rotate and translate based on the tilt of the device.
- **Comic-Style Design:** Bright and colorful visuals combined with smooth motion provide a comic book experience.
- **Gyroscope Sensor Integration:** Real-time motion tracking of the device enhances user interaction and engagement.

## ⚙️ Technologies Used
- **Kotlin**: Programming language for Android development.
- **Jetpack Compose**: For building the responsive and dynamic UI.
- **SensorManager API**: To access the gyroscope sensor and read device tilt.
- **Android**: Target platform for mobile development.

## 🚀 How It Works

1. **Gyroscopic Sensor**: The gyroscope sensor detects rotation around the device’s axes (x, y, and z). This data is used to animate the superhero images on the screen.
2. **Real-Time Animation**: The gyroscope readings are smoothed and applied to rotate and translate images in response to the user’s movement.
3. **Superhero Pages**: Each page displays a different superhero, and users can navigate between them using interactive buttons.

## 🛠 Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/itssagnikmukherjee/GyroMation.git
    ```

2. Open the project in Android Studio.

3. Run the app on a device or emulator with gyroscopic capabilities.


## 🌟 Future Enhancements
- Add more superheroes and comic-like animations.
- Explore advanced gestures for more interactive effects.
- Support for tablets with larger screens.

## 💬 Contributing
Want to help make this project even more amazing? Contributions are welcome! Here's how you can get involved:
1. **Fork the repository** and clone it locally.
2. Create a **new branch** for the feature or bugfix you want to work on :
   ```bash
   git checkout -b feature/your-awesome-feature
   ```
3. Make your changes, then push them back to your fork.
4. Open a Pull Request describing your changes, and I’ll review it as soon as possible! 
---

Feel free to check out the Issues tab for open bugs or feature requests. If you find something you can tackle, jump in and help out! 💻

**Created by [Sagnik Mukherjee](https://github.com/itssagnikmukherjee)**  
If you found this project interesting, give it a ⭐ on GitHub!
