# MELANGE
#### "Multiplatform" Enterprise Library for Applications, Networked and GUI-based, - Extraordinaire

NB: This is still very early stages (pre-alpha), but the infrastructure is coming together nicely.

Core supporting technologies:
 - Spring
 - LWJGL (using utilities from LibGDX)

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

### A brief description of current modules
* melange-common, as seen in JPMS setups, contains commonly accessible enums and interfaces. They have been subdivided and categorized into specific domains for ease of management.
* melange-core, do not touch. Only exports package gbw.melange.core.app which contains the MelangeApplication entrypoint 
* melange-elements, contains the implementations of all element-, constrains, renderes, style definitions, rules, and content renderes. The content renderes themselves is intended to be used through an ECS composition like setup to allow a user 100% type safety through generics, and allowing the system to resolve what content renderer to use underneath.
* melange-events, not quite clear on what will be here yet.
* melange-rules, the fundemental implementations facilitating the current proposed interaction scheme.
* melange-tooling, contains some dev tooling which will be expanded on shortly. However, as it depends on the rest of melange in a bootstrapped-compiler type of situation, it also serves as testing ground for the coherency of it all. Also, when modularized (which is still the plan) it can easily be excluded on a release build.
* melange-mesh, standalone, described in own readme at module root
* melange-model, standalone, described in own readme at module root
* melange-shading, standalone, described in own readme at module root

*multiplatform, it has come to my attention that desktop (Windows & Linux) seems to be the only place where the Lwjgl Backends MELANGE currently uses, are kept up to a date that facilitates features and the OpenGL version that is currently required. This even potentially places restrictions on the version of Java that can be used for a release build. The exact impact of this lack of feature support is not known, but I see but 2 paths: Either reduce the feature set and language level used for Melange, or investigate what platforms to officially drop support for. Given that I don't intend to release instant legacy code (as much as is possible for Java), the former is most likely the path not chosen.

Last Update: 13042024T1126 (ddmmyyyyThhmm)
