# MELANGE
#### Multiplatform Enterprise Library for Applications, Networked and GUI-based, - Extraordinaire

NB: This is still very early stages (pre-alpha), but the infrastructure is coming together nicely.

Core supporting technologies:
 - Spring
 - LibGDX

Core motivations:
 - GUI framworks in Java really have a long road ahead of them. Even those whose road has stretched for nearly 20 years already. 
As the web frameworks slowly but surely convolve on Signals and "compilers", bound to a language unfit for human consumption,
surely an actually compiled language (although in a VM) and the best runtime program-introspection can join the hype.
 - Computers are way better than humans at remembering what values to set for a given outcome, this framework aims to embrace this by taking an alternative approach:
Rather than specifying how much space something takes, you specify the remainder and the rest is resolved at the start of runtime.
 - Most existing gui frameworks suffer from the 2010's XML hype to this day (bearing in mind most havn't been updated since) and go out of their way to include external configuration instead of keeping it simple and clean. I firmly do believe that proper API design can make up for any amount of DSL shenanigans, and will be significantly more maintainable and flexible if done right. 

Cool stuff for free:
 - Since this builds on LibGDX, which is an extension on top of LWJGL, nothing prevents you from actually writing the fragment shader for your background gradient. Of course this amount of freedom and customization requires equally adequate abstractions to manage.
 - A button isn't a square. Its 4 vertexes that happens to form square, and here you can have any amount of vertexes at any possible position. Triangle buttons? Sure. Convex hulls? No problem. Beat that CSS.
 - Hardware acceleration. Additionally, the reactive nature of this framework should allow for a good, stable performance almost regardless of GUI complexity.
 - Not to mention the benefits of not having to deal with "undefined" AND null at the same time. 


