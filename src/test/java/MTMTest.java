import javax.inject.Inject;

import java.io.File;
import java.io.FilenameFilter;

import org.daisy.maven.xproc.xprocspec.XProcSpecRunner;

import org.daisy.pipeline.braille.dotify.DotifyTranslator;

import static org.daisy.pipeline.pax.exam.Options.brailleModule;
import static org.daisy.pipeline.pax.exam.Options.calabashConfigFile;
import static org.daisy.pipeline.pax.exam.Options.domTraversalPackage;
import static org.daisy.pipeline.pax.exam.Options.felixDeclarativeServices;
import static org.daisy.pipeline.pax.exam.Options.forThisPlatform;
import static org.daisy.pipeline.pax.exam.Options.logbackBundles;
import static org.daisy.pipeline.pax.exam.Options.logbackConfigFile;
import static org.daisy.pipeline.pax.exam.Options.pipelineModule;
import static org.daisy.pipeline.pax.exam.Options.thisBundle;
import static org.daisy.pipeline.pax.exam.Options.xprocspecBundles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.ops4j.pax.exam.util.PathUtils;

import static org.ops4j.pax.exam.CoreOptions.bundle;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class MTMTest {
	
	@Inject
	private DotifyTranslator.Provider provider;
	
	@Test
	public void testTranslation() {
		DotifyTranslator translator = provider.get("(locale:sv_SE)").iterator().next();
		assertEquals("⠠⠙⠑⠞⠀⠜⠗⠀⠥⠝⠙⠑⠗⠀⠍⠕⠗⠛⠕⠝⠍⠪⠞⠑⠞⠀⠙⠑⠞⠀⠓⠜⠝⠙⠑⠗⠄⠀⠠⠍⠁⠗⠊⠁⠀⠓⠡⠇⠇⠑⠗⠀⠚⠥⠎⠞⠀⠏⠡⠀⠁⠞⠞⠀⠇⠜⠎⠁⠀⠥⠏⠏⠀⠑⠝⠀⠗⠁⠏⠏⠕⠗⠞⠄",
		             translator.transform("Det är under morgonmötet det händer. Maria håller just på att läsa upp en rapport."));
	}
	
	@Inject
	private XProcSpecRunner xprocspecRunner;
	
	@Test
	public void runXProcSpec() throws Exception {
		File baseDir = new File(PathUtils.getBaseDir());
		boolean success = xprocspecRunner.run(new File(baseDir, "src/test/xprocspec"),
		                                      new File(baseDir, "target/xprocspec-reports"),
		                                      new File(baseDir, "target/surefire-reports"),
		                                      new File(baseDir, "target/xprocspec"),
		                                      new XProcSpecRunner.Reporter.DefaultReporter());
		assertTrue("XProcSpec tests should run with success", success);
	}
	
	@Configuration
	public Option[] config() {
		return options(
			logbackConfigFile(),
			logbackBundles(),
			calabashConfigFile(),
			domTraversalPackage(),
			felixDeclarativeServices(),
			mavenBundle().groupId("com.google.guava").artifactId("guava").versionAsInProject(),
			mavenBundle().groupId("org.daisy.libs").artifactId("jstyleparser").versionAsInProject(),
			mavenBundle().groupId("org.unbescape").artifactId("unbescape").versionAsInProject(),
			mavenBundle().groupId("org.daisy.braille").artifactId("braille-css").versionAsInProject(),
			mavenBundle().groupId("org.apache.servicemix.bundles").artifactId("org.apache.servicemix.bundles.antlr-runtime").versionAsInProject(),
			mavenBundle().groupId("com.googlecode.texhyphj").artifactId("texhyphj").versionAsInProject(),
			mavenBundle().groupId("org.daisy.braille").artifactId("braille-utils.api").versionAsInProject(),
			mavenBundle().groupId("org.daisy.braille").artifactId("braille-utils.pef-tools").versionAsInProject(),
			mavenBundle().groupId("org.daisy.braille").artifactId("braille-utils.impl").versionAsInProject(),
			mavenBundle().groupId("org.daisy.libs").artifactId("jing").versionAsInProject(),
			mavenBundle().groupId("org.daisy.dotify").artifactId("dotify.api").versionAsInProject(),
			mavenBundle().groupId("org.daisy.dotify").artifactId("dotify.common").versionAsInProject(),
			mavenBundle().groupId("org.daisy.dotify").artifactId("dotify.translator.impl").versionAsInProject(),
			mavenBundle().groupId("org.daisy.dotify").artifactId("dotify.hyphenator.impl").versionAsInProject(),
			mavenBundle().groupId("org.daisy.dotify").artifactId("dotify.formatter.impl").versionAsInProject(),
			mavenBundle().groupId("org.daisy.dotify").artifactId("dotify.text.impl").versionAsInProject(),
			mavenBundle().groupId("org.daisy.dotify").artifactId("dotify.task-api").versionAsInProject(),
			mavenBundle().groupId("org.daisy.dotify").artifactId("dotify.task.impl").versionAsInProject(),
			brailleModule("common-utils"),
			brailleModule("css-core"),
			brailleModule("css-calabash"),
			brailleModule("css-utils"),
			brailleModule("dotify-core"),
			brailleModule("dotify-saxon"),
			brailleModule("dotify-calabash"),
			brailleModule("dotify-utils"),
			brailleModule("pef-core"),
			brailleModule("pef-calabash"),
			brailleModule("pef-saxon"),
			brailleModule("pef-utils"),
			pipelineModule("common-utils"),
			pipelineModule("file-utils"),
			thisBundle(),
			xprocspecBundles(),
			junitBundles()
		);
	}
	
}
