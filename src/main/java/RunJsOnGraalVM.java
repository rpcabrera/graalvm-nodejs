import org.apache.commons.io.FileUtils;
import org.graalvm.polyglot.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class RunJsOnGraalVM {

    public static void main(String[] args) {
        // write your code here
        try {
            HostAccess host = HostAccess.newBuilder()
                    .allowPublicAccess(true)
                    .allowAllImplementations(true)
                    .allowListAccess(true)
                    .allowArrayAccess(true)
                    .allowMapAccess(true)
                    .allowBufferAccess(true)
                    .build();


            Map<String, String> options = new HashMap<>();
            // Enable CommonJS experimental support.
            options.put("js.commonjs-require", "true");
            // (optional) folder where the NPM modules to be loaded are located.
            options.put("js.commonjs-require-cwd", "node_modules");
            options.put("js.commonjs-global-properties", "../src/main/resources/globals.js");
            // (optional) Node.js built-in replacements as a comma separated list.
            options.put("js.commonjs-core-modules-replacements",
                    "buffer:buffer/,"+
                            "path:browserify/,"+
                            "crypto:crypto-browserify/"
            );
            options.put("log.js.level","FINE");
            options.put("js.esm-eval-returns-exports","true");

            OutputStream graalvmLogHandler = new ByteArrayOutputStream();
            Path workingSpace = null;
            Engine engine = Engine.newBuilder()
                    .logHandler(graalvmLogHandler)
                    .allowExperimentalOptions(true)
                    .build();
            Context context = Context.newBuilder("js")
                    .allowHostAccess(host)
                    .logHandler(graalvmLogHandler)
                    .allowHostClassLookup(className -> true)
                    .allowExperimentalOptions(true)
                    .engine(engine)
                    .options(options)
                    .allowIO(true)
                    .allowNativeAccess(true)
                    .allowPolyglotAccess(PolyglotAccess.ALL)
                    //.currentWorkingDirectory()
                    .build();

            ClassLoader loader = RunJsOnGraalVM.class.getClassLoader();
            URL resource = loader.getResource("sample.js");
            File file = new File(resource.toURI());
            String jsCode = FileUtils.readFileToString(file);
            context.eval("js", jsCode);

            // Retrieving the ouput
            Value output = context.getBindings("js").getMember("greetings");
            System.out.println("Valor de la variable \"greeting\": "+ output.asString());

            // Debugging output


        }catch( Exception e) {
            System.out.println("Error while executing GraalVM: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
