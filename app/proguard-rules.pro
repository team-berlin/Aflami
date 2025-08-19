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

# ======== Prevent R8 from removing Keep-annotated things ========
#-keepattributes *Annotation*, InnerClasses, EnclosingMethod, Signature, Exceptions, LineNumberTable, LocalVariableTable, LocalVariableTypeTable, SourceFile, RuntimeVisibleAnnotations, RuntimeInvisibleAnnotations, RuntimeVisibleParameterAnnotations, RuntimeInvisibleParameterAnnotations, AnnotationDefault
# --- Ignore optional Google API Client HTTP classes ---
-dontwarn com.google.api.client.http.**
-dontwarn com.google.api.client.http.javanet.**
-dontwarn org.joda.time.**

# Ignore javax/java.awt since they are desktop-only classes
-dontwarn java.awt.**
-dontwarn javax.print.**
-dontwarn javax.swing.**
-dontwarn javax.lang.model.**
-dontwarn javax.tools.**

# Ignore Compose tooling in release
-dontwarn androidx.compose.animation.tooling.**

# Ignore missing classes used only in IDE previews / annotation processing
-dontwarn androidx.compose.**
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
