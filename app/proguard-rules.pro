# ======== General Android/Kotlin ========
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**
-keepclassmembers class kotlin.Metadata { *; }
-keepclassmembers class **$WhenMappings { *; }
-keepclassmembers class kotlin.coroutines.** { *; }
-dontwarn kotlin.Unit
-dontwarn kotlin.jvm.internal.**


# Keep enums & annotations
-keepclassmembers enum * { *; }
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}

# Keep MainActivity & Application
-keep class * extends android.app.Application { *; }
-keep class * extends android.app.Activity { *; }

# ======== Hilt DI ========
# Core Hilt
-keep class dagger.hilt.** { *; }
-dontwarn dagger.hilt.**
-keepnames class dagger.hilt.**

# AndroidX Hilt
-keep class androidx.hilt.** { *; }
-dontwarn androidx.hilt.**
-keepnames class androidx.hilt.**

# Hilt Components and EntryPoints
-keep class * extends dagger.hilt.internal.GeneratedComponent { *; }
-keep class **_HiltModules_* { *; }
-keep class **_Factory { *; }
-keep class * {
    @dagger.hilt.android.lifecycle.HiltViewModel *;
}
-keep class * extends androidx.lifecycle.ViewModel

# Required for dagger
-dontwarn javax.inject.**

# ======== Retrofit ========
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keep interface retrofit2.Call


# Retrofit Models (with Serialization)
-keepclassmembers class * {
    @retrofit2.http.* <methods>;
}

# ======== Kotlin Serialization ========
-keep class kotlinx.serialization.** { *; }
-dontwarn kotlinx.serialization.**

# Required for default enum serialization
-keep @kotlinx.serialization.Serializable class ** {
    *;
}
-keepclasseswithmembers class ** {
    @kotlinx.serialization.SerialName <fields>;
}
-keepclassmembers class ** {
    @kotlinx.serialization.SerialName <fields>;
}

# If you're using polymorphic serialization:
-keepclassmembers class * {
    @kotlinx.serialization.Serializable *;
}

# ======== Room ========
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

# Keep DAOs and Entities
-keep @androidx.room.Dao class * { *; }
-keep @androidx.room.Database class * { *; }
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Relation class * { *; }

# ======== Coil (image loading) ========
-keep class coil.** { *; }
-dontwarn coil.**

# ======== Jetpack Compose (minimal required) ========
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# ======== Firebase ========
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**

# ======== Prevent R8 from removing Keep-annotated things ========
-keepattributes *Annotation*, InnerClasses, EnclosingMethod, Signature, Exceptions, LineNumberTable, LocalVariableTable, LocalVariableTypeTable, SourceFile, RuntimeVisibleAnnotations, RuntimeInvisibleAnnotations, RuntimeVisibleParameterAnnotations, RuntimeInvisibleParameterAnnotations, AnnotationDefault

-dontwarn timber.log.Timber
-dontwarn com.google.api.client.http.**
-dontwarn org.joda.time.**
-dontwarn java.sql.JDBCType
-dontwarn javax.lang.model.SourceVersion
-dontwarn javax.lang.model.element.AnnotationMirror
-dontwarn javax.lang.model.element.AnnotationValue
-dontwarn javax.lang.model.element.AnnotationValueVisitor
-dontwarn javax.lang.model.element.Element
-dontwarn javax.lang.model.element.ElementKind
-dontwarn javax.lang.model.element.ElementVisitor
-dontwarn javax.lang.model.element.ExecutableElement
-dontwarn javax.lang.model.element.Modifier
-dontwarn javax.lang.model.element.Name
-dontwarn javax.lang.model.element.PackageElement
-dontwarn javax.lang.model.element.TypeElement
-dontwarn javax.lang.model.element.TypeParameterElement
-dontwarn javax.lang.model.element.VariableElement
-dontwarn javax.lang.model.type.ArrayType
-dontwarn javax.lang.model.type.DeclaredType
-dontwarn javax.lang.model.type.ExecutableType
-dontwarn javax.lang.model.type.IntersectionType
-dontwarn javax.lang.model.type.PrimitiveType
-dontwarn javax.lang.model.type.TypeKind
-dontwarn javax.lang.model.type.TypeMirror
-dontwarn javax.lang.model.type.TypeVariable
-dontwarn javax.lang.model.type.TypeVisitor
-dontwarn javax.lang.model.util.AbstractElementVisitor8
-dontwarn javax.lang.model.util.ElementFilter
-dontwarn javax.lang.model.util.Elements
-dontwarn javax.lang.model.util.SimpleAnnotationValueVisitor8
-dontwarn javax.lang.model.util.SimpleElementVisitor8
-dontwarn javax.lang.model.util.SimpleTypeVisitor8
-dontwarn javax.lang.model.util.Types
-dontwarn javax.tools.FileObject
-dontwarn javax.tools.JavaFileManager$Location
-dontwarn javax.tools.JavaFileObject
-dontwarn javax.tools.SimpleJavaFileObject
