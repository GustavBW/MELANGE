# MELANGE
#### "Multiplatform" Enterprise Library for Applications, Networked and GUI-based, - Extraordinaire

NB: This is still very early stages (pre-alpha), but the infrastructure is coming together nicely.

Core supporting technologies:
 - Spring
 - OpenGL (using utilities from LibGDX)

Core motivations:
 - GUI framworks in Java really have a long road ahead of them. Even those whose road has stretched for nearly 20 years already. 
As the web frameworks slowly but surely convolve on Signals and "compilers", bound to a language unfit for human consumption,
surely an actually compiled language and the best runtime program-introspection can do just as well, if not better.
 - Computers are way better than humans at remembering what countless constraints and calibrations to set for a given outcome, this framework aims to use CSP to resolve most of this automatically based on a core set of simple rules. 
 - Most existing gui frameworks suffer from the 2010's XML hype to this day (bearing in mind most havn't been updated since) and go out of their way to include external configuration instead of keeping it simple and clean. With proper API design one can make up for any amount of DSL shenanigans, and will be significantly more flexible and intuitive if done right. 

Cool stuff for free:
 - Since this builds on OpenGL, which is an extension on top of LWJGL, nothing prevents you from actually writing the fragment shader for your background gradient. Of course this amount of freedom and customization requires equally adequate abstractions to acquire.
 - A button isn't a square. Its 4 vertexes that happens to form square, and here you can have any amount of vertexes at any possible position. Triangle buttons? Sure. Convex hulls? No problem. For webdev purposes, everything is an svg.
 - Hardware acceleration. Additionally, the reactive nature of this framework should allow for a good, stable performance almost regardless of GUI complexity.
 - Not to mention the benefits of not having to deal with "undefined" AND null at the same time.

## Established Packages of Note
### melange.shading 
##### EXTRACTED, see melange-shading
Contains an entire managed shader templating framework which end goal is establishing and resolving shader graphs as seen from 3D design programs like Blender.
Currently it contains many types of managed, animatable, procedurally generative shaders (voronoi, perlin noise, gradients, & checker) as well as images.  
Additionally, it contains the shader pipeline and user entry point (known as "Colors"), that abstracts 99% of what is actually going on away from the user.  
The package is entirely self-contained (no references to the rest of MELANGE) and is being developed in tandem with the rest of the framework, but the intend is for isolated releases. 
Performance is obtained through caching the latest results as a texture on disk, and using this until invalidation. When I figure out how to duplicate an OpenGL context, this will be spun off as a parallel, self-contained process.

### melange.mesh
##### EXTRACTED, see melange-mesh
Yet to be further expanded, but currently contains lossless mapping utilities from raw vertex data to more processable, extractable formats as well as caching functionality much like melange.shading.
Much like its counterpart, melange.shading, it contains the user entry point (knows as "Shapes") which facilitates further functionality.
The ultimate end-goal is to facilitate generative, procedural modifications like beveling, decimation, voxel-remeshing and the like (also as seen from Blender) which is akind to launching a space program to facilitate a css border radius, but this is too early for compromises.
Performance is obtained through the aforementioned caching, but since this is raw data manipulation with no dependency on an OpenGL context, this will be easily spun off as well. The AbstractMesh class is established specifically for this purpose.
This package is also entirely self-contained with the intention of being able to facilitate independent releases.

### A brief description of the rest
* melange.common, as seen in JPMS setups (although MELANGE is not modularized right now), contains commonly accessible enums and interfaces. They have been subdivided into domains, but it is for visibility purposes.
* melange.core, do not touch. 
* melange.elements, contains the implementations of all element-, constrains, renderes, style definitions, rules, and content renderes. The content renderes themselves is intended to be used through an ECS composition like setup to allow a user 100% type safety through generics, and allowing the system to resolve what content renderer to use underneath.
* melange.events, not quite clear on what will be here yet.
* melange.rules, the fundemental implementations facilitating the current proposed interaction scheme.
* melange.tooling, contains some dev tooling which will be expanded on shortly. However, as it depends on the rest of melange in a bootstrapped-compiler type of situation, it also serves as testing ground for the coherency of it all. Also, when modularized (which is still the plan) it can easily be excluded on a release build.


*multiplatform, it has come to my attention that desktop (Windows & Linux) seems to be the only place where the Lwjgl Backends MELANGE currently uses, are kept up to a date that facilitates features and the OpenGL version that is currently required. This even potentially places restrictions on the version of Java that can be used for a release build. The exact impact of this lack of feature support is not known, but I see but 2 paths: Either reduce the feature set and language level used for Melange, or investigate what platforms to officially drop support for. Given that I don't intend to release instant legacy code (as much as is possible for Java), the former is most likely the path not chosen.


