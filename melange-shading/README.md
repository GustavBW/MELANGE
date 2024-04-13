# Melange Shading Module
Contains an entire shader pipeline and glsl templating framework which end goal is establishing and resolving shader graphs as seen from Blender.
Currently it contains many types of managed, animatable, procedurally generative shaders (voronoi, perlin noise, gradients, & checker) as well as images.  
Additionally, it contains the shader pipeline and user entry point (known as "Colors"), that abstracts 99% of what is actually going on away from the user.  
The package is entirely self-contained (no references to the rest of MELANGE but for melange-common) and is being developed in tandem with the rest of the framework, but the intend is for isolated releases.
Performance is obtained through caching the latest results as a texture on disk, and using this until invalidation. When I figure out how to duplicate an OpenGL context, this will be spun off as a parallel, self-contained process.
