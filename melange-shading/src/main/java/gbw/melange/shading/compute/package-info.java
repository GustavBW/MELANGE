/**
 * Aimed to facilitate the following lifecycle for providing compute shaders. The current lifecycle is estimated to be the following steps:
 * <ol>
 *     <li>The user invokes the compute service to retrieve a builder for a {@link gbw.melange.shading.compute.requests.RequestType#NOW}, {@link gbw.melange.shading.compute.requests.RequestType#LATER} or {@link gbw.melange.shading.compute.requests.RequestType#WHENEVER} request. Any request is reusable and further options will be specified in the builder.</li>
 *     <li>When in the builder, the user can define the threadsafe providers of resources needed. This can be as simple as defining a lambda copying the values in a float array.</li>
 *     <li>When complete, the builder returns an {@link gbw.melange.shading.compute.requests.ExecHandleNow} that takes care of the rest of the lifecycle.</li>
 * </ol>
 */
package gbw.melange.shading.compute;