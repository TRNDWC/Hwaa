package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the `libs` extension.
*/
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final AndroidxLibraryAccessors laccForAndroidxLibraryAccessors = new AndroidxLibraryAccessors(owner);
    private final ConverterLibraryAccessors laccForConverterLibraryAccessors = new ConverterLibraryAccessors(owner);
    private final FirebaseLibraryAccessors laccForFirebaseLibraryAccessors = new FirebaseLibraryAccessors(owner);
    private final GoogleLibraryAccessors laccForGoogleLibraryAccessors = new GoogleLibraryAccessors(owner);
    private final HiltLibraryAccessors laccForHiltLibraryAccessors = new HiltLibraryAccessors(owner);
    private final KotlinxLibraryAccessors laccForKotlinxLibraryAccessors = new KotlinxLibraryAccessors(owner);
    private final OrmliteLibraryAccessors laccForOrmliteLibraryAccessors = new OrmliteLibraryAccessors(owner);
    private final PlayLibraryAccessors laccForPlayLibraryAccessors = new PlayLibraryAccessors(owner);
    private final RecyclerviewLibraryAccessors laccForRecyclerviewLibraryAccessors = new RecyclerviewLibraryAccessors(owner);
    private final SwipeLibraryAccessors laccForSwipeLibraryAccessors = new SwipeLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

        /**
         * Creates a dependency provider for design (com.android.support:design)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getDesign() { return create("design"); }

        /**
         * Creates a dependency provider for glide (com.github.bumptech.glide:glide)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGlide() { return create("glide"); }

        /**
         * Creates a dependency provider for glideCompiler (com.github.bumptech.glide:compiler)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGlideCompiler() { return create("glideCompiler"); }

        /**
         * Creates a dependency provider for gradle (com.android.tools.build:gradle)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGradle() { return create("gradle"); }

        /**
         * Creates a dependency provider for hiltGradlePlugin (com.google.dagger:hilt-android-gradle-plugin)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getHiltGradlePlugin() { return create("hiltGradlePlugin"); }

        /**
         * Creates a dependency provider for junit (junit:junit)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJunit() { return create("junit"); }

        /**
         * Creates a dependency provider for kotlinGradlePlugin (org.jetbrains.kotlin:kotlin-gradle-plugin)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKotlinGradlePlugin() { return create("kotlinGradlePlugin"); }

        /**
         * Creates a dependency provider for library (com.sothree.slidinguppanel:library)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLibrary() { return create("library"); }

        /**
         * Creates a dependency provider for lottie (com.airbnb.android:lottie)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLottie() { return create("lottie"); }

        /**
         * Creates a dependency provider for material (com.google.android.material:material)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getMaterial() { return create("material"); }

        /**
         * Creates a dependency provider for picasso (com.squareup.picasso:picasso)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getPicasso() { return create("picasso"); }

        /**
         * Creates a dependency provider for retrofit (com.squareup.retrofit2:retrofit)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getRetrofit() { return create("retrofit"); }

        /**
         * Creates a dependency provider for timber (com.jakewharton.timber:timber)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getTimber() { return create("timber"); }

    /**
     * Returns the group of libraries at androidx
     */
    public AndroidxLibraryAccessors getAndroidx() { return laccForAndroidxLibraryAccessors; }

    /**
     * Returns the group of libraries at converter
     */
    public ConverterLibraryAccessors getConverter() { return laccForConverterLibraryAccessors; }

    /**
     * Returns the group of libraries at firebase
     */
    public FirebaseLibraryAccessors getFirebase() { return laccForFirebaseLibraryAccessors; }

    /**
     * Returns the group of libraries at google
     */
    public GoogleLibraryAccessors getGoogle() { return laccForGoogleLibraryAccessors; }

    /**
     * Returns the group of libraries at hilt
     */
    public HiltLibraryAccessors getHilt() { return laccForHiltLibraryAccessors; }

    /**
     * Returns the group of libraries at kotlinx
     */
    public KotlinxLibraryAccessors getKotlinx() { return laccForKotlinxLibraryAccessors; }

    /**
     * Returns the group of libraries at ormlite
     */
    public OrmliteLibraryAccessors getOrmlite() { return laccForOrmliteLibraryAccessors; }

    /**
     * Returns the group of libraries at play
     */
    public PlayLibraryAccessors getPlay() { return laccForPlayLibraryAccessors; }

    /**
     * Returns the group of libraries at recyclerview
     */
    public RecyclerviewLibraryAccessors getRecyclerview() { return laccForRecyclerviewLibraryAccessors; }

    /**
     * Returns the group of libraries at swipe
     */
    public SwipeLibraryAccessors getSwipe() { return laccForSwipeLibraryAccessors; }

    /**
     * Returns the group of versions at versions
     */
    public VersionAccessors getVersions() { return vaccForVersionAccessors; }

    /**
     * Returns the group of bundles at bundles
     */
    public BundleAccessors getBundles() { return baccForBundleAccessors; }

    /**
     * Returns the group of plugins at plugins
     */
    public PluginAccessors getPlugins() { return paccForPluginAccessors; }

    public static class AndroidxLibraryAccessors extends SubDependencyFactory {
        private final AndroidxCoreLibraryAccessors laccForAndroidxCoreLibraryAccessors = new AndroidxCoreLibraryAccessors(owner);
        private final AndroidxEspressoLibraryAccessors laccForAndroidxEspressoLibraryAccessors = new AndroidxEspressoLibraryAccessors(owner);
        private final AndroidxFragmentLibraryAccessors laccForAndroidxFragmentLibraryAccessors = new AndroidxFragmentLibraryAccessors(owner);
        private final AndroidxJunitLibraryAccessors laccForAndroidxJunitLibraryAccessors = new AndroidxJunitLibraryAccessors(owner);
        private final AndroidxLegacyLibraryAccessors laccForAndroidxLegacyLibraryAccessors = new AndroidxLegacyLibraryAccessors(owner);
        private final AndroidxLifecycleLibraryAccessors laccForAndroidxLifecycleLibraryAccessors = new AndroidxLifecycleLibraryAccessors(owner);
        private final AndroidxNavigationLibraryAccessors laccForAndroidxNavigationLibraryAccessors = new AndroidxNavigationLibraryAccessors(owner);
        private final AndroidxPaletteLibraryAccessors laccForAndroidxPaletteLibraryAccessors = new AndroidxPaletteLibraryAccessors(owner);
        private final AndroidxRoomLibraryAccessors laccForAndroidxRoomLibraryAccessors = new AndroidxRoomLibraryAccessors(owner);
        private final AndroidxWorkLibraryAccessors laccForAndroidxWorkLibraryAccessors = new AndroidxWorkLibraryAccessors(owner);

        public AndroidxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for activity (androidx.activity:activity)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getActivity() { return create("androidx.activity"); }

            /**
             * Creates a dependency provider for appcompat (androidx.appcompat:appcompat)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getAppcompat() { return create("androidx.appcompat"); }

            /**
             * Creates a dependency provider for constraintlayout (androidx.constraintlayout:constraintlayout)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getConstraintlayout() { return create("androidx.constraintlayout"); }

        /**
         * Returns the group of libraries at androidx.core
         */
        public AndroidxCoreLibraryAccessors getCore() { return laccForAndroidxCoreLibraryAccessors; }

        /**
         * Returns the group of libraries at androidx.espresso
         */
        public AndroidxEspressoLibraryAccessors getEspresso() { return laccForAndroidxEspressoLibraryAccessors; }

        /**
         * Returns the group of libraries at androidx.fragment
         */
        public AndroidxFragmentLibraryAccessors getFragment() { return laccForAndroidxFragmentLibraryAccessors; }

        /**
         * Returns the group of libraries at androidx.junit
         */
        public AndroidxJunitLibraryAccessors getJunit() { return laccForAndroidxJunitLibraryAccessors; }

        /**
         * Returns the group of libraries at androidx.legacy
         */
        public AndroidxLegacyLibraryAccessors getLegacy() { return laccForAndroidxLegacyLibraryAccessors; }

        /**
         * Returns the group of libraries at androidx.lifecycle
         */
        public AndroidxLifecycleLibraryAccessors getLifecycle() { return laccForAndroidxLifecycleLibraryAccessors; }

        /**
         * Returns the group of libraries at androidx.navigation
         */
        public AndroidxNavigationLibraryAccessors getNavigation() { return laccForAndroidxNavigationLibraryAccessors; }

        /**
         * Returns the group of libraries at androidx.palette
         */
        public AndroidxPaletteLibraryAccessors getPalette() { return laccForAndroidxPaletteLibraryAccessors; }

        /**
         * Returns the group of libraries at androidx.room
         */
        public AndroidxRoomLibraryAccessors getRoom() { return laccForAndroidxRoomLibraryAccessors; }

        /**
         * Returns the group of libraries at androidx.work
         */
        public AndroidxWorkLibraryAccessors getWork() { return laccForAndroidxWorkLibraryAccessors; }

    }

    public static class AndroidxCoreLibraryAccessors extends SubDependencyFactory {
        private final AndroidxCoreKtxLibraryAccessors laccForAndroidxCoreKtxLibraryAccessors = new AndroidxCoreKtxLibraryAccessors(owner);

        public AndroidxCoreLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at androidx.core.ktx
         */
        public AndroidxCoreKtxLibraryAccessors getKtx() { return laccForAndroidxCoreKtxLibraryAccessors; }

    }

    public static class AndroidxCoreKtxLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public AndroidxCoreKtxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for ktx (androidx.core:core-ktx)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> asProvider() { return create("androidx.core.ktx"); }

            /**
             * Creates a dependency provider for v160 (androidx.core:core-ktx)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getV160() { return create("androidx.core.ktx.v160"); }

    }

    public static class AndroidxEspressoLibraryAccessors extends SubDependencyFactory {

        public AndroidxEspressoLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for core (androidx.test.espresso:espresso-core)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getCore() { return create("androidx.espresso.core"); }

    }

    public static class AndroidxFragmentLibraryAccessors extends SubDependencyFactory {

        public AndroidxFragmentLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for ktx (androidx.fragment:fragment-ktx)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getKtx() { return create("androidx.fragment.ktx"); }

    }

    public static class AndroidxJunitLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public AndroidxJunitLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for junit (androidx.test.ext:junit)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> asProvider() { return create("androidx.junit"); }

            /**
             * Creates a dependency provider for ktx (androidx.test.ext:junit-ktx)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getKtx() { return create("androidx.junit.ktx"); }

    }

    public static class AndroidxLegacyLibraryAccessors extends SubDependencyFactory {
        private final AndroidxLegacySupportLibraryAccessors laccForAndroidxLegacySupportLibraryAccessors = new AndroidxLegacySupportLibraryAccessors(owner);

        public AndroidxLegacyLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at androidx.legacy.support
         */
        public AndroidxLegacySupportLibraryAccessors getSupport() { return laccForAndroidxLegacySupportLibraryAccessors; }

    }

    public static class AndroidxLegacySupportLibraryAccessors extends SubDependencyFactory {

        public AndroidxLegacySupportLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for v4 (androidx.legacy:legacy-support-v4)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getV4() { return create("androidx.legacy.support.v4"); }

    }

    public static class AndroidxLifecycleLibraryAccessors extends SubDependencyFactory {
        private final AndroidxLifecycleLivedataLibraryAccessors laccForAndroidxLifecycleLivedataLibraryAccessors = new AndroidxLifecycleLivedataLibraryAccessors(owner);
        private final AndroidxLifecycleViewmodelLibraryAccessors laccForAndroidxLifecycleViewmodelLibraryAccessors = new AndroidxLifecycleViewmodelLibraryAccessors(owner);

        public AndroidxLifecycleLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for extensions (androidx.lifecycle:lifecycle-extensions)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getExtensions() { return create("androidx.lifecycle.extensions"); }

        /**
         * Returns the group of libraries at androidx.lifecycle.livedata
         */
        public AndroidxLifecycleLivedataLibraryAccessors getLivedata() { return laccForAndroidxLifecycleLivedataLibraryAccessors; }

        /**
         * Returns the group of libraries at androidx.lifecycle.viewmodel
         */
        public AndroidxLifecycleViewmodelLibraryAccessors getViewmodel() { return laccForAndroidxLifecycleViewmodelLibraryAccessors; }

    }

    public static class AndroidxLifecycleLivedataLibraryAccessors extends SubDependencyFactory {

        public AndroidxLifecycleLivedataLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for ktx (androidx.lifecycle:lifecycle-livedata-ktx)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getKtx() { return create("androidx.lifecycle.livedata.ktx"); }

    }

    public static class AndroidxLifecycleViewmodelLibraryAccessors extends SubDependencyFactory {

        public AndroidxLifecycleViewmodelLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for ktx (androidx.lifecycle:lifecycle-viewmodel-ktx)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getKtx() { return create("androidx.lifecycle.viewmodel.ktx"); }

    }

    public static class AndroidxNavigationLibraryAccessors extends SubDependencyFactory {
        private final AndroidxNavigationFragmentLibraryAccessors laccForAndroidxNavigationFragmentLibraryAccessors = new AndroidxNavigationFragmentLibraryAccessors(owner);
        private final AndroidxNavigationUiLibraryAccessors laccForAndroidxNavigationUiLibraryAccessors = new AndroidxNavigationUiLibraryAccessors(owner);

        public AndroidxNavigationLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at androidx.navigation.fragment
         */
        public AndroidxNavigationFragmentLibraryAccessors getFragment() { return laccForAndroidxNavigationFragmentLibraryAccessors; }

        /**
         * Returns the group of libraries at androidx.navigation.ui
         */
        public AndroidxNavigationUiLibraryAccessors getUi() { return laccForAndroidxNavigationUiLibraryAccessors; }

    }

    public static class AndroidxNavigationFragmentLibraryAccessors extends SubDependencyFactory {

        public AndroidxNavigationFragmentLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for ktx (androidx.navigation:navigation-fragment-ktx)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getKtx() { return create("androidx.navigation.fragment.ktx"); }

    }

    public static class AndroidxNavigationUiLibraryAccessors extends SubDependencyFactory {

        public AndroidxNavigationUiLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for ktx (androidx.navigation:navigation-ui-ktx)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getKtx() { return create("androidx.navigation.ui.ktx"); }

    }

    public static class AndroidxPaletteLibraryAccessors extends SubDependencyFactory {

        public AndroidxPaletteLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for ktx (androidx.palette:palette-ktx)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getKtx() { return create("androidx.palette.ktx"); }

    }

    public static class AndroidxRoomLibraryAccessors extends SubDependencyFactory {

        public AndroidxRoomLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for compiler (androidx.room:room-compiler)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getCompiler() { return create("androidx.room.compiler"); }

            /**
             * Creates a dependency provider for ktx (androidx.room:room-ktx)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getKtx() { return create("androidx.room.ktx"); }

    }

    public static class AndroidxWorkLibraryAccessors extends SubDependencyFactory {
        private final AndroidxWorkRuntimeLibraryAccessors laccForAndroidxWorkRuntimeLibraryAccessors = new AndroidxWorkRuntimeLibraryAccessors(owner);

        public AndroidxWorkLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at androidx.work.runtime
         */
        public AndroidxWorkRuntimeLibraryAccessors getRuntime() { return laccForAndroidxWorkRuntimeLibraryAccessors; }

    }

    public static class AndroidxWorkRuntimeLibraryAccessors extends SubDependencyFactory {

        public AndroidxWorkRuntimeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for ktx (androidx.work:work-runtime-ktx)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getKtx() { return create("androidx.work.runtime.ktx"); }

    }

    public static class ConverterLibraryAccessors extends SubDependencyFactory {

        public ConverterLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for gson (com.squareup.retrofit2:converter-gson)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getGson() { return create("converter.gson"); }

    }

    public static class FirebaseLibraryAccessors extends SubDependencyFactory {
        private final FirebaseAnalyticsLibraryAccessors laccForFirebaseAnalyticsLibraryAccessors = new FirebaseAnalyticsLibraryAccessors(owner);
        private final FirebaseAuthLibraryAccessors laccForFirebaseAuthLibraryAccessors = new FirebaseAuthLibraryAccessors(owner);
        private final FirebaseCommonLibraryAccessors laccForFirebaseCommonLibraryAccessors = new FirebaseCommonLibraryAccessors(owner);
        private final FirebaseDatabaseLibraryAccessors laccForFirebaseDatabaseLibraryAccessors = new FirebaseDatabaseLibraryAccessors(owner);
        private final FirebaseStorageLibraryAccessors laccForFirebaseStorageLibraryAccessors = new FirebaseStorageLibraryAccessors(owner);

        public FirebaseLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for bom (com.google.firebase:firebase-bom)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getBom() { return create("firebase.bom"); }

        /**
         * Returns the group of libraries at firebase.analytics
         */
        public FirebaseAnalyticsLibraryAccessors getAnalytics() { return laccForFirebaseAnalyticsLibraryAccessors; }

        /**
         * Returns the group of libraries at firebase.auth
         */
        public FirebaseAuthLibraryAccessors getAuth() { return laccForFirebaseAuthLibraryAccessors; }

        /**
         * Returns the group of libraries at firebase.common
         */
        public FirebaseCommonLibraryAccessors getCommon() { return laccForFirebaseCommonLibraryAccessors; }

        /**
         * Returns the group of libraries at firebase.database
         */
        public FirebaseDatabaseLibraryAccessors getDatabase() { return laccForFirebaseDatabaseLibraryAccessors; }

        /**
         * Returns the group of libraries at firebase.storage
         */
        public FirebaseStorageLibraryAccessors getStorage() { return laccForFirebaseStorageLibraryAccessors; }

    }

    public static class FirebaseAnalyticsLibraryAccessors extends SubDependencyFactory {

        public FirebaseAnalyticsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for ktx (com.google.firebase:firebase-analytics-ktx)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getKtx() { return create("firebase.analytics.ktx"); }

    }

    public static class FirebaseAuthLibraryAccessors extends SubDependencyFactory {

        public FirebaseAuthLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for ktx (com.google.firebase:firebase-auth-ktx)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getKtx() { return create("firebase.auth.ktx"); }

    }

    public static class FirebaseCommonLibraryAccessors extends SubDependencyFactory {

        public FirebaseCommonLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for ktx (com.google.firebase:firebase-common-ktx)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getKtx() { return create("firebase.common.ktx"); }

    }

    public static class FirebaseDatabaseLibraryAccessors extends SubDependencyFactory {

        public FirebaseDatabaseLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for ktx (com.google.firebase:firebase-database-ktx)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getKtx() { return create("firebase.database.ktx"); }

    }

    public static class FirebaseStorageLibraryAccessors extends SubDependencyFactory {

        public FirebaseStorageLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for ktx (com.google.firebase:firebase-storage-ktx)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getKtx() { return create("firebase.storage.ktx"); }

    }

    public static class GoogleLibraryAccessors extends SubDependencyFactory {
        private final GoogleFirebaseLibraryAccessors laccForGoogleFirebaseLibraryAccessors = new GoogleFirebaseLibraryAccessors(owner);

        public GoogleLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for services (com.google.gms:google-services)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getServices() { return create("google.services"); }

        /**
         * Returns the group of libraries at google.firebase
         */
        public GoogleFirebaseLibraryAccessors getFirebase() { return laccForGoogleFirebaseLibraryAccessors; }

    }

    public static class GoogleFirebaseLibraryAccessors extends SubDependencyFactory {
        private final GoogleFirebaseAuthLibraryAccessors laccForGoogleFirebaseAuthLibraryAccessors = new GoogleFirebaseAuthLibraryAccessors(owner);

        public GoogleFirebaseLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at google.firebase.auth
         */
        public GoogleFirebaseAuthLibraryAccessors getAuth() { return laccForGoogleFirebaseAuthLibraryAccessors; }

    }

    public static class GoogleFirebaseAuthLibraryAccessors extends SubDependencyFactory {

        public GoogleFirebaseAuthLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for ktx (com.google.firebase:firebase-auth-ktx)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getKtx() { return create("google.firebase.auth.ktx"); }

    }

    public static class HiltLibraryAccessors extends SubDependencyFactory {

        public HiltLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for android (com.google.dagger:hilt-android)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getAndroid() { return create("hilt.android"); }

            /**
             * Creates a dependency provider for compiler (com.google.dagger:hilt-compiler)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getCompiler() { return create("hilt.compiler"); }

    }

    public static class KotlinxLibraryAccessors extends SubDependencyFactory {
        private final KotlinxCoroutinesLibraryAccessors laccForKotlinxCoroutinesLibraryAccessors = new KotlinxCoroutinesLibraryAccessors(owner);

        public KotlinxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at kotlinx.coroutines
         */
        public KotlinxCoroutinesLibraryAccessors getCoroutines() { return laccForKotlinxCoroutinesLibraryAccessors; }

    }

    public static class KotlinxCoroutinesLibraryAccessors extends SubDependencyFactory {

        public KotlinxCoroutinesLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for android (org.jetbrains.kotlinx:kotlinx-coroutines-android)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getAndroid() { return create("kotlinx.coroutines.android"); }

            /**
             * Creates a dependency provider for core (org.jetbrains.kotlinx:kotlinx-coroutines-core)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getCore() { return create("kotlinx.coroutines.core"); }

    }

    public static class OrmliteLibraryAccessors extends SubDependencyFactory {

        public OrmliteLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for android (com.j256.ormlite:ormlite-android)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getAndroid() { return create("ormlite.android"); }

    }

    public static class PlayLibraryAccessors extends SubDependencyFactory {
        private final PlayServicesLibraryAccessors laccForPlayServicesLibraryAccessors = new PlayServicesLibraryAccessors(owner);

        public PlayLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Returns the group of libraries at play.services
         */
        public PlayServicesLibraryAccessors getServices() { return laccForPlayServicesLibraryAccessors; }

    }

    public static class PlayServicesLibraryAccessors extends SubDependencyFactory {

        public PlayServicesLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for auth (com.google.android.gms:play-services-auth)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getAuth() { return create("play.services.auth"); }

    }

    public static class RecyclerviewLibraryAccessors extends SubDependencyFactory {

        public RecyclerviewLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for v7 (com.android.support:recyclerview-v7)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getV7() { return create("recyclerview.v7"); }

    }

    public static class SwipeLibraryAccessors extends SubDependencyFactory {

        public SwipeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for card (com.github.yuyakaido:cardstackview)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getCard() { return create("swipe.card"); }

    }

    public static class VersionAccessors extends VersionFactory  {

        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Returns the version associated to this alias: activity (1.9.2)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getActivity() { return getVersion("activity"); }

            /**
             * Returns the version associated to this alias: agp (8.5.2)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getAgp() { return getVersion("agp"); }

            /**
             * Returns the version associated to this alias: appcompat (1.7.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getAppcompat() { return getVersion("appcompat"); }

            /**
             * Returns the version associated to this alias: constraintlayout (2.1.4)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getConstraintlayout() { return getVersion("constraintlayout"); }

            /**
             * Returns the version associated to this alias: converterGson (2.11.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getConverterGson() { return getVersion("converterGson"); }

            /**
             * Returns the version associated to this alias: coreKtx (1.13.1)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getCoreKtx() { return getVersion("coreKtx"); }

            /**
             * Returns the version associated to this alias: coreKtxVersion (1.6.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getCoreKtxVersion() { return getVersion("coreKtxVersion"); }

            /**
             * Returns the version associated to this alias: design (28.0.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getDesign() { return getVersion("design"); }

            /**
             * Returns the version associated to this alias: espressoCore (3.6.1)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getEspressoCore() { return getVersion("espressoCore"); }

            /**
             * Returns the version associated to this alias: firebaseAuthKtx (23.0.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getFirebaseAuthKtx() { return getVersion("firebaseAuthKtx"); }

            /**
             * Returns the version associated to this alias: firebaseAuthKtxVersion (23.1.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getFirebaseAuthKtxVersion() { return getVersion("firebaseAuthKtxVersion"); }

            /**
             * Returns the version associated to this alias: firebaseBom (33.2.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getFirebaseBom() { return getVersion("firebaseBom"); }

            /**
             * Returns the version associated to this alias: firebaseCommonKtx (21.0.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getFirebaseCommonKtx() { return getVersion("firebaseCommonKtx"); }

            /**
             * Returns the version associated to this alias: fragmentKtx (1.8.3)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getFragmentKtx() { return getVersion("fragmentKtx"); }

            /**
             * Returns the version associated to this alias: glide (4.15.1)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getGlide() { return getVersion("glide"); }

            /**
             * Returns the version associated to this alias: googleServices (4.4.2)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getGoogleServices() { return getVersion("googleServices"); }

            /**
             * Returns the version associated to this alias: gradle (8.0.2)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getGradle() { return getVersion("gradle"); }

            /**
             * Returns the version associated to this alias: hiltAndroid (2.52)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getHiltAndroid() { return getVersion("hiltAndroid"); }

            /**
             * Returns the version associated to this alias: hiltCompiler (2.52)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getHiltCompiler() { return getVersion("hiltCompiler"); }

            /**
             * Returns the version associated to this alias: junit (4.13.2)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getJunit() { return getVersion("junit"); }

            /**
             * Returns the version associated to this alias: junitKtx (1.2.1)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getJunitKtx() { return getVersion("junitKtx"); }

            /**
             * Returns the version associated to this alias: junitVersion (1.2.1)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getJunitVersion() { return getVersion("junitVersion"); }

            /**
             * Returns the version associated to this alias: kotlin (1.8.20)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getKotlin() { return getVersion("kotlin"); }

            /**
             * Returns the version associated to this alias: kotlinxCoroutinesCore (1.7.3)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getKotlinxCoroutinesCore() { return getVersion("kotlinxCoroutinesCore"); }

            /**
             * Returns the version associated to this alias: legacySupportV4 (1.0.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getLegacySupportV4() { return getVersion("legacySupportV4"); }

            /**
             * Returns the version associated to this alias: library (3.4.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getLibrary() { return getVersion("library"); }

            /**
             * Returns the version associated to this alias: lifecycleExtensions (2.2.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getLifecycleExtensions() { return getVersion("lifecycleExtensions"); }

            /**
             * Returns the version associated to this alias: lifecycleLivedataKtx (2.8.5)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getLifecycleLivedataKtx() { return getVersion("lifecycleLivedataKtx"); }

            /**
             * Returns the version associated to this alias: lifecycleViewmodelKtx (2.8.5)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getLifecycleViewmodelKtx() { return getVersion("lifecycleViewmodelKtx"); }

            /**
             * Returns the version associated to this alias: material (1.12.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getMaterial() { return getVersion("material"); }

            /**
             * Returns the version associated to this alias: ormliteAndroid (4.48)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getOrmliteAndroid() { return getVersion("ormliteAndroid"); }

            /**
             * Returns the version associated to this alias: paletteKtx (1.0.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getPaletteKtx() { return getVersion("paletteKtx"); }

            /**
             * Returns the version associated to this alias: picasso (2.8)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getPicasso() { return getVersion("picasso"); }

            /**
             * Returns the version associated to this alias: playServicesAuth (21.2.0)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getPlayServicesAuth() { return getVersion("playServicesAuth"); }

            /**
             * Returns the version associated to this alias: roomKtx (2.6.1)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getRoomKtx() { return getVersion("roomKtx"); }

            /**
             * Returns the version associated to this alias: workRuntimeKtx (2.9.1)
             * If the version is a rich version and that its not expressible as a
             * single version string, then an empty string is returned.
             * This version was declared in catalog libs.versions.toml
             */
            public Provider<String> getWorkRuntimeKtx() { return getVersion("workRuntimeKtx"); }

    }

    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

    }

    public static class PluginAccessors extends PluginFactory {
        private final AndroidPluginAccessors paccForAndroidPluginAccessors = new AndroidPluginAccessors(providers, config);
        private final DaggerPluginAccessors paccForDaggerPluginAccessors = new DaggerPluginAccessors(providers, config);
        private final GooglePluginAccessors paccForGooglePluginAccessors = new GooglePluginAccessors(providers, config);
        private final JetbrainsPluginAccessors paccForJetbrainsPluginAccessors = new JetbrainsPluginAccessors(providers, config);
        private final KotlinPluginAccessors paccForKotlinPluginAccessors = new KotlinPluginAccessors(providers, config);

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of plugins at plugins.android
         */
        public AndroidPluginAccessors getAndroid() { return paccForAndroidPluginAccessors; }

        /**
         * Returns the group of plugins at plugins.dagger
         */
        public DaggerPluginAccessors getDagger() { return paccForDaggerPluginAccessors; }

        /**
         * Returns the group of plugins at plugins.google
         */
        public GooglePluginAccessors getGoogle() { return paccForGooglePluginAccessors; }

        /**
         * Returns the group of plugins at plugins.jetbrains
         */
        public JetbrainsPluginAccessors getJetbrains() { return paccForJetbrainsPluginAccessors; }

        /**
         * Returns the group of plugins at plugins.kotlin
         */
        public KotlinPluginAccessors getKotlin() { return paccForKotlinPluginAccessors; }

    }

    public static class AndroidPluginAccessors extends PluginFactory {

        public AndroidPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Creates a plugin provider for android.application to the plugin id 'com.android.application'
             * This plugin was declared in catalog libs.versions.toml
             */
            public Provider<PluginDependency> getApplication() { return createPlugin("android.application"); }

    }

    public static class DaggerPluginAccessors extends PluginFactory {
        private final DaggerHiltPluginAccessors paccForDaggerHiltPluginAccessors = new DaggerHiltPluginAccessors(providers, config);

        public DaggerPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of plugins at plugins.dagger.hilt
         */
        public DaggerHiltPluginAccessors getHilt() { return paccForDaggerHiltPluginAccessors; }

    }

    public static class DaggerHiltPluginAccessors extends PluginFactory {
        private final DaggerHiltAndroidPluginAccessors paccForDaggerHiltAndroidPluginAccessors = new DaggerHiltAndroidPluginAccessors(providers, config);

        public DaggerHiltPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of plugins at plugins.dagger.hilt.android
         */
        public DaggerHiltAndroidPluginAccessors getAndroid() { return paccForDaggerHiltAndroidPluginAccessors; }

    }

    public static class DaggerHiltAndroidPluginAccessors extends PluginFactory {

        public DaggerHiltAndroidPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Creates a plugin provider for dagger.hilt.android.plugin to the plugin id 'dagger.hilt.android.plugin'
             * This plugin was declared in catalog libs.versions.toml
             */
            public Provider<PluginDependency> getPlugin() { return createPlugin("dagger.hilt.android.plugin"); }

    }

    public static class GooglePluginAccessors extends PluginFactory {

        public GooglePluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Creates a plugin provider for google.services to the plugin id 'com.google.gms.google-services'
             * This plugin was declared in catalog libs.versions.toml
             */
            public Provider<PluginDependency> getServices() { return createPlugin("google.services"); }

    }

    public static class JetbrainsPluginAccessors extends PluginFactory {
        private final JetbrainsKotlinPluginAccessors paccForJetbrainsKotlinPluginAccessors = new JetbrainsKotlinPluginAccessors(providers, config);

        public JetbrainsPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Returns the group of plugins at plugins.jetbrains.kotlin
         */
        public JetbrainsKotlinPluginAccessors getKotlin() { return paccForJetbrainsKotlinPluginAccessors; }

    }

    public static class JetbrainsKotlinPluginAccessors extends PluginFactory {

        public JetbrainsKotlinPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Creates a plugin provider for jetbrains.kotlin.android to the plugin id 'org.jetbrains.kotlin.android'
             * This plugin was declared in catalog libs.versions.toml
             */
            public Provider<PluginDependency> getAndroid() { return createPlugin("jetbrains.kotlin.android"); }

    }

    public static class KotlinPluginAccessors extends PluginFactory {

        public KotlinPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

            /**
             * Creates a plugin provider for kotlin.kapt to the plugin id 'kotlin-kapt'
             * This plugin was declared in catalog libs.versions.toml
             */
            public Provider<PluginDependency> getKapt() { return createPlugin("kotlin.kapt"); }

    }

}
